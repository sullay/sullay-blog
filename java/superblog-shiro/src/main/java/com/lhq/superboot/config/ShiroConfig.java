package com.lhq.superboot.config;

import com.lhq.superboot.service.ShiroService;
import com.lhq.superboot.shiro.LocalCredentialsMatcher;
import com.lhq.superboot.shiro.LocalRealm;
import com.lhq.superboot.shiro.LocalWebSecurityManager;
import com.lhq.superboot.shiro.cache.ShiroRedisCacheManager;
import com.lhq.superboot.shiro.filter.LocalAuthorizationFilter;
import com.lhq.superboot.shiro.filter.LocalPermissionsAuthorizationFilter;
import com.lhq.superboot.shiro.property.ShiroConfigProperty;
import com.lhq.superboot.shiro.session.RedisSessionDAO;
import com.lhq.superboot.shiro.session.ShiroSessionFactory;
import com.lhq.superboot.shiro.session.ShiroSessionListener;
import com.lhq.superboot.shiro.session.ShiroSessionManager;
import com.lhq.superboot.util.RedisUtils;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: shiro主配置
 *
 * @author: lihaoqi
 * @date: 2019年5月19日
 * @version: 1.0.0
 */
@Configuration
@AutoConfigureAfter(ShiroLifecycleBeanPostProcessorConfig.class)
public class ShiroConfig {

	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

	@Autowired
	private ShiroConfigProperty shiroProp;

	@Resource
	private RedisUtils redisUtils;

	/**
	 * @Description: 验证方式加入容器
	 */
	@Bean(name = "localRealm")
	public LocalRealm localRealm() {
		logger.info("[Shiro] -> localRealm()");
		LocalRealm localRealm = new LocalRealm();
		// 允许缓存
		localRealm.setCachingEnabled(true);

		// 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
		localRealm.setAuthenticationCachingEnabled(true);
		localRealm.setAuthenticationCacheName(shiroProp.getCacheAuthenticName());

		// 启用授权缓存，即缓存AuthorizationInfo信息，默认false
		localRealm.setAuthorizationCachingEnabled(true);
		localRealm.setAuthorizationCacheName(shiroProp.getCacheAuthorizaName());

		// 密码加密
		localRealm.setCredentialsMatcher(localCredentialsMatcher());
		return localRealm;
	}

	/**
	 * @Description: 权限管理，配置主要是Realm的管理认证
	 */
	@Bean
	public LocalWebSecurityManager securityManager(LocalRealm localRealm, RedisUtils redisUtils) {
		logger.info("[Shiro] -> securityManager()");
		LocalWebSecurityManager securityManager = new LocalWebSecurityManager();
		// 设置自定义realm
		securityManager.setRealm(localRealm);
		// 设置rememberMe管理器: 前后端分离，服务器无需保存用户状态
		// securityManager.setRememberMeManager(rememberMeManager());
		// 配置 缓存管理类 使用redis缓存
		securityManager.setCacheManager(cacheManager());
		// 自定义session管理 使用redis缓存
		securityManager.setSessionManager(sessionManager());

		return securityManager;
	}

	public SessionManager sessionManager() {
		ShiroSessionManager sessionManager = new ShiroSessionManager();
		Collection<SessionListener> listeners = new ArrayList<>();
		// 配置监听
		listeners.add(sessionListener());
		sessionManager.setSessionListeners(listeners);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		sessionManager.setSessionDAO(sessionDAO());
		sessionManager.setCacheManager(cacheManager());
		sessionManager.setSessionFactory(sessionFactory());

		// 全局会话超时时间（单位毫秒），默认30分钟 暂时设置为10秒钟 用来测试
		sessionManager.setGlobalSessionTimeout(shiroProp.getSessionTimeout());
		// 是否开启删除无效的session对象 默认为true
		sessionManager.setDeleteInvalidSessions(true);
		// 是否开启定时调度器进行检测过期session 默认为true
		sessionManager.setSessionValidationSchedulerEnabled(true);
		// 设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
		// 设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler
		// 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
		sessionManager.setSessionValidationInterval(shiroProp.getSessionValid());
		// 取消url 后面的 JSESSIONID
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		return sessionManager;
	}

	/**
	 * @Description: 配置session监听
	 */
	public ShiroSessionListener sessionListener() {
		return new ShiroSessionListener();
	}

	/**
	 * @Description: 配置保存sessionId的cookie 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理
	 * 	也需要自己的cookie 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
	 */
	public SimpleCookie sessionIdCookie() {
		// 这个参数是cookie的名称
		SimpleCookie simpleCookie = new SimpleCookie(shiroProp.getSessionIdName());
		// setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数
		// 设为true后，只能通过http访问，javascript无法访问
		// 防止xss读取cookie
		simpleCookie.setHttpOnly(true);
		simpleCookie.setPath("/");
		// maxAge=-1表示浏览器关闭时失效此Cookie
		simpleCookie.setMaxAge(-1);
		return simpleCookie;
	}

	/**
	 * @Description: SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件 MemorySessionDAO 直接在内存中进行会话维护
	 * 	EnterpriseCacheSessionDAO
	 * 	提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
	 */
	@Bean
	public SessionDAO sessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisUtils);
		// session在redis中的保存时间,最好大于session会话超时时间
		redisSessionDAO.setKeyPrefix(shiroProp.getSessionPrefix());
		redisSessionDAO.setExpire(shiroProp.getSessionExpire());
		redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
		return redisSessionDAO;
	}

	@Bean
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		return new JavaUuidSessionIdGenerator();
	}

	@Bean("sessionFactory")
	public ShiroSessionFactory sessionFactory() {
		return new ShiroSessionFactory();
	}

	/**
	 * @Description: cacheManager 缓存 redis实现
	 */
	public ShiroRedisCacheManager cacheManager() {
		// 使用默认
		// RedisCacheManager redisCacheManager = new RedisCacheManager();
		// redisCacheManager.setRedisManager(redisManager());
		// redisCacheManager.setKeyPrefix(shiroProp.getCachePrefix());
		// return redisCacheManager;
		// 使用自己的（自定义redis命名）
		ShiroRedisCacheManager redisCacheManager = new ShiroRedisCacheManager();
		redisCacheManager.setRedisManager(redisUtils);
		redisCacheManager.setExpire(shiroProp.getCacheExpire());
		return redisCacheManager;
	}

	/**
	 * @Description: cookie对象;
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		logger.info("[Shiro] -> rememberMeCookie()");
		// 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie(shiroProp.getCookieName());
		// <!-- 记住我cookie生效时间30天（259200） ,单位秒;-->
		simpleCookie.setMaxAge(shiroProp.getCookieTimeout());
		return simpleCookie;
	}

	/**
	 * @Description: rememberme管理器 cookie管理对象; 可加密，默认aes加密
	 * cookieRememberMeManager.setCipherKey(Base64.decode("16位key"));
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		logger.info("[Shiro] -> rememberMeManager()");
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		return cookieRememberMeManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
			LocalWebSecurityManager securityManager) {
		logger.info("[Shiro] -> authorizationAttributeSourceAdvisor()");
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * @Description: 凭证匹配器
	 */
	@Bean
	public LocalCredentialsMatcher localCredentialsMatcher() {
		LocalCredentialsMatcher hashedCredentialsMatcher = new LocalCredentialsMatcher();
		// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		// 散列的次数，比如散列两次，相当于 md5(md5(""));
		hashedCredentialsMatcher.setHashIterations(2);
		return hashedCredentialsMatcher;
	}

	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean() {
		FilterRegistrationBean<DelegatingFilterProxy> filterRegistration = new FilterRegistrationBean<>();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}

	/**
	 * @Description: Filter工厂，设置对应的过滤条件和跳转条件
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(LocalWebSecurityManager securityManager, ShiroService shiroService) {
		logger.info("[Shiro] -> shiroFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>(2);
		filterMap.put("error", new LocalAuthorizationFilter());
		filterMap.put("perms", new LocalPermissionsAuthorizationFilter());
		// filterMap.put("authc", localFormAuthenticationFilter);
		// filterMap.put("user", localShiroUserFilter);
		// filterMap.put("roles", localRolesAuthorizationFilter);

		// 设置登录界面，这边不做界面控制，返回未登录状态即可
		// shiroFilterFactoryBean.setLoginUrl(shiroProp.getUnloginUrl());
		// 登录成功后要跳转的链接(一般默认在js中跳转)
		// shiroFilterFactoryBean.setSuccessUrl("/index.html");
		// 未授权跳转

		shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroService.loadFilterChainDefinitions());
		shiroFilterFactoryBean.setFilters(filterMap);
		return shiroFilterFactoryBean;
	}

}
