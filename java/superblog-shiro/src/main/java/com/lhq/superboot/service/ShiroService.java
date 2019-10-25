package com.lhq.superboot.service;

import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

/**
 * @Description: shrioService
 *
 * @author: lihaoqi
 * @date: 2019年5月19日
 * @version: 1.0.0
 */
public interface ShiroService {

	/**
	 * @Description: 初始化权限
	 */
	public Map<String, String> loadFilterChainDefinitions();

	/**
	 * @Description: 在对角色进行增删改操作时，需要调用此方法进行动态刷新
	 * 
	 * @param shiroFilterFactoryBean
	 */
	public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean);

}
