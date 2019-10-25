package com.lhq.superboot.exception;

import com.lhq.superboot.util.PropMsgUtils;

/**
 * @Description: 异常处理类
 * 
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public class SuperBootException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String code;

	private String detailMessage = null;
	
	public SuperBootException() {
	}

	public SuperBootException(Throwable t) {
		super(t);
		this.detailMessage = t.getMessage();
	}
	
	public SuperBootException(String code) {
		this.code = code;
		this.detailMessage = initMsg(code, null);
	}

	public SuperBootException(String code, Throwable t) {
		super(t);
		this.code = code;
		this.detailMessage = initMsg(code, t);
	}

	@Override
	public String getMessage() {
		return detailMessage;
	}

	public SuperBootException(String code, Object... args) {
		this.code = code;
		this.detailMessage = initMsg(code, null, args);
	}

	public SuperBootException(String code, Throwable t, Object... args) {
		super(t);
		this.code = code;
		this.detailMessage = initMsg(code, t, args);
	}

	public String getCode() {
		return this.code;
	}

	private static String initMsg(String code, Throwable t, Object... args) {
		String str = PropMsgUtils.getExceptionMsg(code, args);
		if (null != str) {
			return str;
		}
		return t == null ? code : t.getMessage();
	}

	public static String getMsg(String code, Object... args) {
		return PropMsgUtils.getExceptionMsg(code, args);
	}
}
