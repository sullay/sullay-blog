package com.lhq.superboot.shiro.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

/**
 * @Description: 解决跨域问题
 * 
 * @author: lihaoqi
 * @date: 2019年4月27日
 * @version: 1.0.0
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*")
public class LocalShiroCorsFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(LocalShiroCorsFilter.class);

	@SuppressWarnings("unused")
	private FilterConfig config = null;

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	@Override
	public void destroy() {
		this.config = null;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		logger.debug("[shiro] -> 进入跨域过滤器");
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		// 允许哪些Origin发起跨域请求,nginx下正常
		String origin = request.getHeader("Origin");
		if (origin == null) {
			origin = request.getHeader("Referer");
		}
		response.setHeader("Access-Control-Allow-Origin", origin);
		// 允许请求的方法
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
		// 多少秒内，不需要再发送预检验请求，可以缓存该结果
		response.setHeader("Access-Control-Max-Age", "3600");
		// 表明它允许跨域请求包含xxx头
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization, Content-Type, Depth, User-Agent, X-File-Size, X-Requested-With, X-Requested-By, If-Modified-Since, X-File-Name, X-File-Type, Cache-Control, Origin");
		// 是否允许浏览器携带用户身份信息（cookie）
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Expose-Headers", "*");
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(200);
			return;
		}
		filterChain.doFilter(servletRequest, response);
	}
}
