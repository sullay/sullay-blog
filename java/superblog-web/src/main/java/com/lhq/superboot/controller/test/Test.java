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

	public static void main(String[] args) {
		String a = "";
		String b = "";
		for (int i = 1; i <= 10; i++) {
			if (i%2 == 0) {
				a += i == 10 ? i : i + ",";
			} else {
				b += i + ",";
			}
		}
		System.out.println(b + a);
		
		String c = "";
		for (int i = 1; i <= 10; i++) {
			if (i == 10) {
				System.out.print(c + i);
			}
			if (i%2 != 0) {
				System.out.print(i + ",");
			} else {
				c += i + ",";
			}
		}
		
		System.out.println();
		for (int i = 1; i <= 20; i++) {
			if (i < 10 && i%2 != 0) {
				System.out.print(i + ",");
			}
			if (i > 10 && i%2 == 0) {
				if (i != 20) {
					System.out.print(i-10 + ",");
				} else {
					System.out.print(i-10);
				}
				
			}
		}
	}

}
