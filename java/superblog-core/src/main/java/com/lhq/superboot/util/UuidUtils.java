package com.lhq.superboot.util;

import java.util.UUID;

/**
 * @Description: 生成uuid
 *
 * @author: lihaoqi
 * @date: 2019年4月22日
 * @version: 1.0.0
 */
public class UuidUtils {

	/**
	 * @Description: 获取uuid
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
