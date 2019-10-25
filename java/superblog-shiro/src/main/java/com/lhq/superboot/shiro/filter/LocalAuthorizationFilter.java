package com.lhq.superboot.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lhq.superboot.enums.LoginMsg;

/**
 * @Description: 重写没有权限与没有登录的过滤器
 * 
 * @author: lihaoqi
 * @date: 2019年4月27日
 * @version: 1.0.0
 */
public class LocalAuthorizationFilter extends AccessControlFilter {
	private static final Logger logger = LoggerFactory.getLogger(LocalAuthorizationFilter.class);
	
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {

		Subject subject = getSubject(request, response);
		String[] perms = (String[]) mappedValue;

		boolean isPermitted = true;
		if (perms != null && perms.length > 0) {
			if (perms.length == 1) {
				if (!subject.isPermitted(perms[0])) {
					isPermitted = false;
				}
			} else {
				if (!subject.isPermittedAll(perms)) {
					isPermitted = false;
				}
			}
		}

		return isPermitted;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		logger.error("[shiro] -> 进入登录失败过滤器");
		Subject subject = getSubject(request, response);
		// 未登录
		if (subject.getPrincipal() == null) {
			super.saveRequest(request);
			WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, LoginMsg.UNLOGIN.getCode());
		} else {
			WebUtils.toHttp(response).sendError(HttpServletResponse.SC_FORBIDDEN, LoginMsg.UNAUTH.getCode());
		}
		return false;
	}

}
