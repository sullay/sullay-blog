package com.lhq.superboot.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: lifecycleBeanPostProcessor影响bean注入，分离该配置
 * 
 * @author: lihaoqi
 * @date: 2019年4月18日
 * @version: 1.0.0
 */
@Configuration
public class ShiroLifecycleBeanPostProcessorConfig {

	 /**
     * @Description: Shiro生命周期处理器
     *
     * @return
     */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
