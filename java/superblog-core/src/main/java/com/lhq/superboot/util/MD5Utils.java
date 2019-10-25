package com.lhq.superboot.util;

import java.security.MessageDigest;

/**
 * @Description md5加密
 *
 * @author: lihaoqi 
 * @date: 2019年7月24日 下午2:54:10 
 * @version: v1.0.0
 */
public class MD5Utils {

	/**
	 * @Description: 生成32位大写 MD5
	 *
	 * @param data 待处理数据
	 * @return MD5
	 */
	public static String MD5(String data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] array = md.digest(data.getBytes("UTF-8"));
		StringBuilder sb = new StringBuilder();
		for (byte item : array) {
			sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString().toUpperCase();
	}
}
