package com.lhq.superboot.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.lhq.superboot.exception.SuperBootException;

/**
  *  @Description: 异常处理工具
 * 
 * @author: lihaoqi
 * @date: 2019年4月13日
 * @version: v1.0.0
 */
public class ExceptionUtils {
	
	/**
	 * @Description: 拼凑异常堆栈信息
	 * 
	 * @param msg
	 * @param t
	 * @return
	 */
	public static StringBuilder getException(String msg, Throwable t) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(msg)) {
			sb.append(msg).append("\r");
		}
		StringBuilder sbStr = getException(t);
		sb.append(sbStr);
		return sb;
	}

	/**
	 * @Description: 获取异常堆栈信息
	 * 
	 * @param t
	 * @return
	 */
	public static StringBuilder getException(Throwable t) {
		StringBuilder sb = new StringBuilder();
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bas);
		t.printStackTrace(ps);
		// 全部堆栈
		//sb.append(new String(bas.toByteArray()));
		// 简洁的错误信息
		sb.append(t.toString());
		ps.close();
		try {
			bas.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb;
	}
	
	/**
	 * @Description: 循环获取异常信息（获取最内部的错误）
	 */
	public static String getExceptionMsg(Throwable e) {
		while (e != null) {
			if (e instanceof SuperBootException) {
				return ((SuperBootException) e).getMessage();
			}
			Throwable tex = e.getCause();
			if (tex == null) {
				return e.getMessage();
			} else {
				e = tex;
			}
		}
		return null;
	}
}
