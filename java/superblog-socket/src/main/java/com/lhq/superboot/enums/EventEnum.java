package com.lhq.superboot.enums;

/**
 * @Description: 微信通知返回值code
 *
 * @author: lihaoqi 
 * @date: 2019年7月4日 下午2:23:55 
 * @version: v1.0.0
 */
public enum EventEnum {

    NOTICE_EVENT("notice","用户后台提醒事件"),  
      
    LOGIN_EVENT("login","登录事件");

	private final String value;
	private final String code;

	EventEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}
	
	public String getValue() {
		return value;
	}
}
