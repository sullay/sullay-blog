package com.lhq.superboot.enums;

/**
 * @Description: 登录来源
 * 
 * @author: lihaoqi
 * @version: 1.0.0
 * @date: 2019年4月19日
 */
public enum LoginSource {

	PC("PC端"),

	XCX("小程序"), 
	
	HT("后台管理");

	private String code;

	private LoginSource(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
