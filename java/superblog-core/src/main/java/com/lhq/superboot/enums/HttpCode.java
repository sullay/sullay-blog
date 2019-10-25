package com.lhq.superboot.enums;

/**
 * @Description: 响应码枚举，参考HTTP状态码的语义
 * 
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public enum HttpCode {

	// 成功
	SUCCESS(200),

	// 失败
	FAIL(400),

	// 未认证（签名错误）
	UNAUTHORIZED(401),

	// 接口不存在
	NOT_FOUND(404),

	// 服务器内部错误
	INTERNAL_SERVER_ERROR(500);

	private int code;

	HttpCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}