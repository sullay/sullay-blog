package com.lhq.superboot.shiro.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 解决单次请求需要多次访问redis
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public class ShiroSessionManager extends DefaultWebSessionManager {

	private static Logger logger = LoggerFactory.getLogger(ShiroSessionManager.class);

	/**
	 * @Description: 获取session 优化单次请求需要多次访问redis的问题
	 * 
	 * @param sessionKey
	 * @return
	 * @throws UnknownSessionException
	 */
	@Override
	protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
		Serializable sessionId = getSessionId(sessionKey);
		ServletRequest request = null;
		if (sessionKey instanceof WebSessionKey) {
			request = ((WebSessionKey) sessionKey).getServletRequest();
		}
		if (request != null && null != sessionId) {
			Object sessionObj = request.getAttribute(sessionId.toString());
			if (sessionObj != null) {
				logger.debug("[Shiro] -> read session from request, request url: {} ");
				return (Session) sessionObj;
			}
		}

		Session session = super.retrieveSession(sessionKey);
		if (request != null && null != sessionId) {
			request.setAttribute(sessionId.toString(), session);
		}
		logger.debug("[Shiro] -> read request url {}", request != null ?((HttpServletRequest) request).getRequestURI() : "");
		return session;
	}
}
