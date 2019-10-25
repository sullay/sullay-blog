package com.lhq.superboot.service.impl;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhq.superboot.service.PhoneService;
import com.lhq.superboot.util.RedisUtils;
import com.lhq.superboot.util.StringUtils;
import com.lhq.superboot.util.UuidUtils;

/**
 * @Description: 手机实现
 * 
 * @author: lihaoqi
 * @date: 2019年4月24日
 * @version: 1.0.0
 */
@Service
public class PhoneServiceImpl implements PhoneService {

	private static final Logger logger = LoggerFactory.getLogger(PhoneService.class);

	/** '忘记密码'随机数缓存前缀 **/
	private static final String FP_KEY = "lhq:superboot:phone:fp:";

	/** '忘记密码'随机数缓存时间(秒) **/
	private static final int FP_EXPIRE = 300;

	/** '忘记密码'存储分隔符 **/
	private static final String FP_SPLIT = "#";

	@Autowired
	private RedisUtils redisUtils;

	@Override
	public String setPhoneRandomInCache(String phone) {
		// 生成uuid随机数，与手机号对应存入redis缓存
		String random = UuidUtils.getUuid();
		redisUtils.set(FP_KEY + phone, random, FP_EXPIRE);

		// 返回手机号与随机数简单加密形成的token
		String token = Base64.getEncoder().encodeToString(random.getBytes()) + FP_SPLIT + phone;
		return Base64.getEncoder().encodeToString(token.getBytes());
	}

	@Override
	public boolean validatePhoneRandom(String token) {
		String random = null;
		String phone = null;
		Object code = null;
		try {
			String tokenStr = new String(Base64.getDecoder().decode(token));

			random = new String(Base64.getDecoder().decode(tokenStr.split(FP_SPLIT)[0]));
			phone = tokenStr.split(FP_SPLIT)[1];
			if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(random)) {
				return false;
			}
			code = redisUtils.get(FP_KEY + phone);
			if (code == null) {
				return false;
			}
			if (!code.toString().equals(random)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		} finally {
			if (null != code) {
				redisUtils.del(FP_KEY + phone);
			}
		}
	}

}
