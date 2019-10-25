package com.lhq.superboot.shiro.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Description: shiro密码加密工具
 *
 * @author: lihaoqi
 * @date: 2019年4月23日
 * @version: 1.0.0
 */
public class ShiroMd5Util {

	/** 加密方式 **/
	private static final String hashAlgorithmName = "MD5";

	/** 加密次数 **/
	private static final int hashIterations = 2;

	/** 添加user的密码加密方法 */
	public static String PwdMd5(Object crdentials) {

		SimpleHash hash = new SimpleHash(hashAlgorithmName, crdentials, null,  hashIterations);

		return hash.toString();
	}
}
