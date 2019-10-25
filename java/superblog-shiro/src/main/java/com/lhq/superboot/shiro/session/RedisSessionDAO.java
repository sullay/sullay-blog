package com.lhq.superboot.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lhq.superboot.util.RedisUtils;

/**
 * @Description: 由于修改了cacheManager，重写RedisSessionDAO
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 * 
 */
public class RedisSessionDAO extends AbstractSessionDAO  {

	private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    private static final String DEFAULT_SESSION_KEY_PREFIX = "shiro:session:";
    private String keyPrefix = DEFAULT_SESSION_KEY_PREFIX;

    private static final long DEFAULT_SESSION_IN_MEMORY_TIMEOUT = 1000L;
    /**
     * doReadSession be called about 10 times when login.
     * Save Session in ThreadLocal to resolve this problem. sessionInMemoryTimeout is expiration of Session in ThreadLocal.
     * The default value is 1000 milliseconds (1s).
     * Most of time, you don't need to change it.
     */
    private long sessionInMemoryTimeout = DEFAULT_SESSION_IN_MEMORY_TIMEOUT;

    /**
     * expire time in seconds
     */
    private static final int DEFAULT_EXPIRE = -2;
    private static final int NO_EXPIRE = -1;

    /**
     * Please make sure expire is longer than sesion.getTimeout()
     */
    private int expire = DEFAULT_EXPIRE;

    private static final int MILLISECONDS_IN_A_SECOND = 1000;

    private RedisUtils redisUtils;
    
    @SuppressWarnings("rawtypes")
	private static ThreadLocal sessionsInThread = new ThreadLocal();

    @Override
    public void update(Session session) throws UnknownSessionException {
    	logger.debug("[Shiro] -> session update");
        //如果会话过期/停止 没必要再更新了
        try {
            if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                return;
            }

            if (session instanceof ShiroSession) {
                // 如果没有主要字段(除lastAccessTime以外其他字段)发生改变
                ShiroSession ss = (ShiroSession) session;
                if (!ss.isChanged()) {
                    return;
                }
                //如果没有返回 证明有调用 setAttribute往redis 放的时候永远设置为false
                ss.setChanged(false);
            }

            this.saveSession(session);
        } catch (Exception e) {
            logger.warn("update Session is failed", e);
        }
    }

    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        String key = getRedisSessionKey(session.getId());
        if (expire == DEFAULT_EXPIRE) {
            this.redisUtils.set(key, session, (int) (session.getTimeout() / MILLISECONDS_IN_A_SECOND));
            return;
        }
        if (expire != NO_EXPIRE && expire * MILLISECONDS_IN_A_SECOND < session.getTimeout()) {
            logger.warn("Redis session expire time: "
                    + (expire * MILLISECONDS_IN_A_SECOND)
                    + " is less than Session timeout: "
                    + session.getTimeout()
                    + " . It may cause some problems.");
        }
        this.redisUtils.set(key, session, expire);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        try {
            redisUtils.del(getRedisSessionKey(session.getId()));
        } catch (Exception e) {
            logger.error("delete session error. session id= {}",session.getId());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        try {
            Set<String> keys = redisUtils.scan(this.keyPrefix + "*");
            if (keys != null && keys.size() > 0) {
                for (String key:keys) {
                    Session s = (Session) redisUtils.get(key);
                    sessions.add(s);
                }
            }
        } catch (Exception e) {
            logger.error("get active sessions error.");
        }
        return sessions;
    }

    public Long getActiveSessionsSize() {
        Long size = 0L;
        try {
            size = redisUtils.scanSize(this.keyPrefix + "*");
        } catch (Exception e) {
            logger.error("get active sessions error.");
        }
        return size;
    }

    @Override
    protected Serializable doCreate(Session session) {
    	logger.debug("[Shiro] -> session doCreate");
        if (session == null) {
            logger.error("session is null");
            throw new UnknownSessionException("session is null");
        }
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.warn("session id is null");
            return null;
        }
        Session s = getSessionFromThreadLocal(sessionId);

        if (s != null) {
            return s;
        }

        logger.debug("[Shiro] -> read session from redis");
        try {
            s = (Session) redisUtils.get(getRedisSessionKey(sessionId));
            setSessionToThreadLocal(sessionId, s);
        } catch (Exception e) {
            logger.error("read session error. settionId= {}",sessionId);
        }
        return s;
    }

    @SuppressWarnings("unchecked")
	private void setSessionToThreadLocal(Serializable sessionId, Session s) {
        Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
        if (sessionMap == null) {
            sessionMap = new HashMap<Serializable, SessionInMemory>();
            sessionsInThread.set(sessionMap);
        }
        SessionInMemory sessionInMemory = new SessionInMemory();
        sessionInMemory.setCreateTime(new Date());
        sessionInMemory.setSession(s);
        sessionMap.put(sessionId, sessionInMemory);
    }

    @SuppressWarnings("unchecked")
	private Session getSessionFromThreadLocal(Serializable sessionId) {
        Session s = null;

        if (sessionsInThread.get() == null) {
            return null;
        }

        Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
        SessionInMemory sessionInMemory = sessionMap.get(sessionId);
        if (sessionInMemory == null) {
            return null;
        }
        Date now = new Date();
        long duration = now.getTime() - sessionInMemory.getCreateTime().getTime();
        if (duration < sessionInMemoryTimeout) {
            s = sessionInMemory.getSession();
            logger.debug("read session from memory");
        } else {
            sessionMap.remove(sessionId);
        }

        return s;
    }

    private String getRedisSessionKey(Serializable sessionId) {
        return this.keyPrefix + sessionId;
    }

    public RedisUtils getRedisManager() {
        return redisUtils;
    }

    public void setRedisManager(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public long getSessionInMemoryTimeout() {
        return sessionInMemoryTimeout;
    }

    public void setSessionInMemoryTimeout(long sessionInMemoryTimeout) {
        this.sessionInMemoryTimeout = sessionInMemoryTimeout;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
