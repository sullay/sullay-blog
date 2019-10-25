package com.lhq.superboot.enums;

/**
 * @Description: 注册相关信息
 *
 * @author: lihaoqi
 * @date: 2019年4月24日
 * @version: 1.0.0
 */
public enum RegisterMsg {
	
	WECHATREGISTERERROR("微信小程序注册失败"),
	
	APPREGISTERERRO("APP注册失败"),
	
	REPEATUSERNAME("账号已存在"),

	REGISTEREDPHONE("手机已经注册");

	private String code;

	RegisterMsg(String code) {
	      this.code = code;
	   }

	public String getCode() {
		return code;
	}
}
