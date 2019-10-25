package com.lhq.superboot.domain;

/**
 * @Description: 返回对象实体
 * 
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public class Result<T> {

	private int code;

	private String msg;

	private T data;
	
	private String stack;
	
	private String sessionId;

	public int getCode() {
		return code;
	}

	public Result<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Result<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}

	public String getStack() {
		return stack;
	}

	public Result<T> setStack(String stack) {
		this.stack = stack;
		return this;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
