package com.lhq.superboot.shiro.session;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhq.superboot.util.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
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

	private static final String X_AUTH_TOKEN = "SessionId";

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

	@Override
	public Serializable getSessionId(SessionKey key) {
		Serializable id = super.getSessionId(key);
		if (StringUtils.isEmpty(id) && WebUtils.isWeb(key)) {
			ServletRequest request = WebUtils.getRequest(key);
			ServletResponse response = WebUtils.getResponse(key);
			id = getSessionId(request, response);
		}
		return id;
	}

	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		return getReferencedSessionId(request, response);
	}

	//获取sessionid
	private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {
		String id = this.getSessionIdHeaderValue(request, response);

		//DefaultWebSessionManager 中代码 直接copy过来
		if (id != null) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
		}
		//不会把sessionid放在URL后
		request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, Boolean.FALSE);
		return id;
	}

	// 请求头中获取 sessionId 并把sessionId 放入 response 中
	private String getSessionIdHeaderValue(ServletRequest request, ServletResponse response) {
		if (!(request instanceof HttpServletRequest)) {
			logger.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
			return null;
		}
		else {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

			// 在request 中 读取 x-auth-token 信息  作为 sessionId
			String sessionId = httpRequest.getHeader(X_AUTH_TOKEN);

			// 每次读取之后 都把当前的 sessionId 放入 response 中
			HttpServletResponse httpResponse = (HttpServletResponse) response;

			if (StringUtils.isNotEmpty(sessionId)) {
				httpResponse.setHeader(X_AUTH_TOKEN, sessionId);
				logger.info("Current session ID is {}", sessionId);
			}

			return sessionId;
		}
	}

}
