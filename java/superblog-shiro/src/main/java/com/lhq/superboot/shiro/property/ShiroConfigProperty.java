package com.lhq.superboot.shiro.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Description: 将配置文件读取
 *
 * @author: lihaoqi
 * @date: 2019年4月18日
 * @version: 1.0.0
 */
@Data
@Component
public class ShiroConfigProperty {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Value("${spring.redis.password}")
	private String redisPwd;
	
	@Value("${shiro.cache.expire}")
	private int cacheExpire;
	
	@Value("${shiro.cache.prefix}")
	private String cachePrefix;

	@Value("${shiro.cache.authenticName}")
	private String cacheAuthenticName;

	@Value("${shiro.cache.authorizaName}")
	private String cacheAuthorizaName;
	
	@Value("${shiro.session.timeout}")
	private int sessionTimeout;
	
	@Value("${shiro.session.idName}")
	private String sessionIdName;
	
	@Value("${shiro.session.prefix}")
	private String sessionPrefix;

	@Value("${shiro.session.valid}")
	private long sessionValid;
	
	@Value("${shiro.session.expire}")
	private int sessionExpire;

	@Value("${shiro.cookie.name}")
	private String cookieName;
	
	@Value("${shiro.cookie.timeout}")
	private int cookieTimeout;

	@Value("${shiro.filter.authUrl}")
	private String authUrl;
	
	@Value("${shiro.login.accountlockTime}")
	private int accountlockTime;
	
	@Value("${shiro.login.accountlockCount}")
	private int accountlockCount;
	
	@Value("${shiro.login.repeatlockCount}")
	private int repeatlockCount;
	
	@Value("${shiro.login.repeatlockTime}")
	private int repeatlockTime;
	
	@Value("${shiro.login.repeatlockPrefix}")
	private String repeatlockPrefix;
	
	@Value("${shiro.login.accountlockPrefix}")
	private String accountlockPrefix;

}
