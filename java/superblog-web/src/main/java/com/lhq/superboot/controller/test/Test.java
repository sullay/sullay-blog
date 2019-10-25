package com.lhq.superboot.controller.test;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * @Description: 测试接口类
 *
 * @author: lihaoqi
 * @date: 2019年7月30日 上午11:17:39
 * @version: v1.0.0
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/test")
@Api(value = "测试controller", tags = { "测试操作接口" })
public class Test {


    @GetMapping("/sms")
    public Object getUserToken() {
    	return "1";
    }


}
