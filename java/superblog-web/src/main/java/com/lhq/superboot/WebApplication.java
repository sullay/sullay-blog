package com.lhq.superboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @Description: 启动类
 * 
 * @author: lihaoqi
 * @date: 2019年4月15日
 * @version: 1.0.0
 * @since 1.8
 */
@MapperScan(basePackages = {"com.lhq.superboot.mapper","com.lhq.superboot.repository"})
@SpringBootApplication
@EnableScheduling
public class WebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
	    // 解决netty冲突
        System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(WebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}
	
	@Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
