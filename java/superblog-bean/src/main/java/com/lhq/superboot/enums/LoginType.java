package com.lhq.superboot.enums;

/**
 * @Description: 登录类型
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public enum LoginType {

	// 密码登录
	PASSWORD("password"),

	// 免密登录
	NOPASSWD("nopassword");

	private String code;

	private LoginType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
