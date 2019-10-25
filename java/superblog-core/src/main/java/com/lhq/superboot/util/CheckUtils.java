package com.lhq.superboot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @Description: 校验工具
 *
 * @author: lihaoqi
 * @date: 2019年6月13日 上午10:37:37
 * @version: v1.0.0
 */
public class CheckUtils {

	/** 密码正则校验 **/
	private static final String CHECK_PASSWORD_PATTERN = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_@!]{6,15}$";

	/** 大陆内地与香港手机校验 **/
	private static final String CHECK_PHONE_PATTERN = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[6-8])|(166)|(19[8,9]))\\d{8}$|^((00)?852(-)?)?[6|9]\\d{7}$";
	
	/** 邮箱校验 **/
	private static final String CHECK_EMAIL_PATTERN = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * @Description: 校验密码
	 * 
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password) {
		Pattern regex = Pattern.compile(CHECK_PASSWORD_PATTERN);
		Matcher matcher = regex.matcher(password);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * @Description: 校验手机合法
	 * 
	 * @param phone
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static boolean isPhoneLegal(String phone) throws PatternSyntaxException {
		return isChinaAndHKPhoneLegal(phone);
	}

	private static boolean isChinaAndHKPhoneLegal(String phone) throws PatternSyntaxException {
		Pattern p = Pattern.compile(CHECK_PHONE_PATTERN);
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	
	/**
	 * @Description 校验邮箱
	 * @param password
	 * @return
	 */
	public static boolean isEmailLegal(String email) {
		Pattern regex = Pattern.compile(CHECK_EMAIL_PATTERN);
		Matcher matcher = regex.matcher(email);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
}
