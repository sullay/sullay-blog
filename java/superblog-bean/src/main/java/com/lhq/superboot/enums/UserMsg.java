package com.lhq.superboot.enums;

/**
 * @Description: 用户信息
 *
 * @author: lihaoqi
 * @date: 2019年4月25日
 * @version: 1.0.0
 */
public enum UserMsg {

	REGISTERSUCCESS("用户注册成功"),
	
	MODIFYPWDSUCCESS("用户修改密码成功"),
	
	MODIFYMESSSUCCESS("用户信息修改成功"),
	
	BINDPHONESUCCESS("绑定手机成功"),
	
	GETCODESUCCESS("获取短信验证码成功");

	private String code;

	UserMsg(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
