package com.lhq.superboot.shiro.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lhq.superboot.enums.LoginMsg;
import com.lhq.superboot.util.ResultUtils;

/**
 * @Description: 权限过滤器
 *
 * @author: lihaoqi
 * @date: 2019年4月29日
 * @version: 1.0.0
 */
public class LocalPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

	private static final Logger logger = LoggerFactory.getLogger(LocalPermissionsAuthorizationFilter.class);

	/**
	 * @Description: 在访问过来的时候检测是否为OPTIONS请求，如果是就直接返回true
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			logger.debug("[shiro] -> 进入预处理");
			setHeader(httpRequest, httpResponse);
			return true;
		}
		return super.preHandle(request, response);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		logger.debug("[shiro] -> 进入登录失败过滤器");
		Subject subject = getSubject(request, response);
		// 未登录与未授权
		if (subject.getPrincipal() == null) {
			super.saveRequest(request);
			//WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, LoginMsg.UNLOGIN.getCode());
			setResponse(response, request, HttpStatus.UNAUTHORIZED.value(), LoginMsg.UNLOGIN.getCode());
		} else {
			//WebUtils.toHttp(response).sendError(HttpServletResponse.SC_FORBIDDEN, LoginMsg.UNAUTH.getCode());
			setResponse(response, request, HttpStatus.FORBIDDEN.value(), LoginMsg.UNAUTH.getCode());
		}
		return false;
	}

	/**
	 * @Description: 为response设置header，实现跨域
	 */
	private void setHeader(HttpServletRequest request, HttpServletResponse response) {
		String origin = request.getHeader("Origin");
		if (origin == null) {
			origin = request.getHeader("Referer");
		}
		// 跨域的header设置
		response.setHeader("Access-control-Allow-Origin", origin);
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization, Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, X-Requested-By, If-Modified-Since, X-File-Name, X-File-Type, Cache-Control, Origin");
		response.setHeader("Content-Type", request.getHeader("Content-Type"));
		response.setStatus(HttpStatus.OK.value());
	}

	/**
	 * @Description: 返回错误数据
	 */
	private void setResponse(ServletResponse response, ServletRequest request, int code, String msg) {
		logger.debug("[shiro] -> 返回失败信息:{}", msg);
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		setHeader(httpRequest, httpResponse);
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = httpResponse.getWriter();
			out.append(ResultUtils.error(code, msg));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
