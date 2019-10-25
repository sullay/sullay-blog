package com.lhq.superboot.enums;

/**
 * @Description: 登录信息
 * 
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public enum LoginMsg {
	
	// login模块
	LOGINSUCCESS("登录成功"),

	LOGOUTSUCCESS("登出成功"),

	UNLOGIN("未登录系统"),

	UNAUTH("没有访问权限"),

	UNKNOWNACCOUNT("不存在该用户"),

	DISABLED("该用户已被禁用"),

	EMPTYACCOUNT("账号为空"),

	EMPTYPASSWORD("密码为空"),

	INCORRECTPWD("密码错误"),
	
	UNKNOWNSOURCE("未知来源"),
	
	PHONEEMPTY("手机号为空"),
	
	PHONECODEERROR("手机验证码错误"),
	
	WECHATCODE("小程序js_code为空"),
	
	WECHATTOKENEMPTY("小程序token为空"),
	
	TOKENSPLITERROR("无法正确解析token值"),
	
	NOSESSIONID("无法获取sessionid"),
	
	APPTOKENEMPTY("app token为空"),
	
	APPSESSIONEXPIRE("app session已过期");

	private String code;

	LoginMsg(String code) {
	      this.code = code;
	   }

	public String getCode() {
		return code;
	}
}
