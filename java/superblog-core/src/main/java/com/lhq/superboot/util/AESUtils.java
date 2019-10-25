package com.lhq.superboot.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: AES加密解密工具类
 * 
 * @author: lihaoqi
 * @date: 2019年7月23日
 * @version: v2.0.0
 */
@Component
public class AESUtils {

	private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

	private static final String defaultCharset = "UTF-8";
	private static final String KEY_AES = "AES";
	private static final String KEY_CIPHER = "AES/ECB/PKCS5Padding";

	private static String key;

	@Value("${aes.key}")
	public void setKey(String aesKey) {
		key = aesKey;
	}

	/**
	 * @Description: 使用默认key加密
	 *
	 * @param data
	 * @return
	 */
	public static String encrypt(String data) {
		return encrypt(data, key);
	}

	/**
	 * @Description: 使用传入key加密
	 *
	 * @param sSrc
	 * @param sKey
	 * @return
	 */
	public static String encrypt(String data, String key) {
		if (key == null) {
			logger.error("加密key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (key.length() != 16) {
			logger.error("Key长度不是16位");
			return null;
		}
		byte[] raw = null;
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
			Cipher cipher = Cipher.getInstance(KEY_CIPHER);// "算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(data.getBytes(defaultCharset));

			return new Base64().encodeToString(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		} catch (Exception e) {
			logger.error("加密异常，异常信息：", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Description: 使用默认key解密
	 *
	 * @param data
	 * @return
	 */
	public static String decrypt(String data) {
		return decrypt(data, key);
	}

	/**
	 * @Description: 使用传入key解密
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String decrypt(String data, String key) {
		// 判断Key是否正确
		if (key == null) {
			logger.error("加密key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (key.length() != 16) {
			logger.error("Key长度不是16位");
			return null;
		}
		try {
			byte[] raw = key.getBytes(defaultCharset);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
			Cipher cipher = Cipher.getInstance(KEY_CIPHER);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted = new Base64().decode(data);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted);
			
			return new String(original, defaultCharset);
		} catch (Exception e) {
			logger.error("加密异常，异常信息：", e.getMessage());
			return null;
		}
	}
}