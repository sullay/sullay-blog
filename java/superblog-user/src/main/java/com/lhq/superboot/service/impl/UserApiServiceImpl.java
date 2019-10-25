package com.lhq.superboot.service.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhq.superboot.domain.User;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.service.UserApiService;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.util.MD5Utils;
import com.lhq.superboot.util.RedisUtils;
import com.lhq.superboot.util.StringUtils;
import com.lhq.superboot.util.UuidUtils;

/**
 * @Description: 对外接口api相关实现
 *
 * @author: lihaoqi 
 * @date: 2019年7月24日 上午10:31:59 
 * @version: v1.0.0
 */
@Service
public class UserApiServiceImpl implements UserApiService {

	private static final Logger logger = LoggerFactory.getLogger(UserApiService.class);
	
	/** key存储空间名 **/
	private static final String KEY_NAMESPACE = "lhq:superboot:api:key:";
	/** secret存储空间名 **/
	private static final String SECRET_NAMESPACE = "lhq:superboot:api:secret:";
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Autowired
	private UserService userService;
	
	@Override
	public String getApiKey() {
		String userId =  (String) SecurityUtils.getSubject().getPrincipal();
		if (StringUtils.isEmpty(userId)) {
			throw new SuperBootException("lhq-superboot-user-0015");
		}
		return getApiKey(userId);
	}
	
	@Override
	public String getApiKey(String userId) {
		Object key = redisUtils.get(KEY_NAMESPACE + userId);
		String apiKey = null;
		if (key == null) {
			//创建唯一key
			int count = 0;
			while (true) {
				if (count > 10000) {
					throw new SuperBootException("lhq-superboot-user-0018");
				}
				apiKey = UuidUtils.getUuid().substring(0, 16);
				if (redisUtils.get(KEY_NAMESPACE + apiKey) == null) {
					// 不存在返回，存在继续生成
					break;
				}
				count++;
			}
			logger.debug("用户{}第一次生成apikey：{}", userId, apiKey);
			// 生成双向索引
			redisUtils.set(KEY_NAMESPACE + userId, apiKey);
			redisUtils.set(KEY_NAMESPACE + apiKey, userId);
		} else {
			apiKey = (String) key;
			logger.debug("用户{}获取apikey：{}", userId, apiKey);
		}
		return apiKey;
	}
	
	@Override
	public String getApiSecret(String appKey) {
		if (StringUtils.isEmpty(appKey)) {
			throw new SuperBootException("lhq-superboot-user-0016");
		}
		Object secret = redisUtils.get(SECRET_NAMESPACE + appKey);
		String apiSecret = null;
		if (secret == null) {
			apiSecret = UuidUtils.getUuid();
			redisUtils.set(SECRET_NAMESPACE + appKey, apiSecret);
		} else {
			apiSecret = (String) secret;
		}
		logger.debug("apikey：{}的apiSecret：{}", appKey, apiSecret);
		return apiSecret;
	}

	@Override
	public String createApiSign(Map<String, String> param, String appKey) {
		Set<String> keySet = param.keySet();
		String[] keyArray = (String[]) keySet.toArray(new String[keySet.size()]);
		Arrays.sort(keyArray);
		StringBuilder sb = new StringBuilder();
		for (String k : keyArray) {
			if (((String) param.get(k)).trim().length() > 0) {
				sb.append(k).append("=").append(((String) param.get(k)).trim()).append("&");
			}
		}
		
		try {
			return MD5Utils.MD5(MD5Utils.MD5(sb.toString()) + getApiSecret(appKey));
		} catch (Exception e) {
			logger.debug("签名失败{}", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public User getUserByApikey(String appKey) {
		if (StringUtils.isEmpty(appKey)) {
			throw new SuperBootException("lhq-superboot-user-0016");
		}
		Object userId = redisUtils.get(KEY_NAMESPACE + appKey);
		if (userId == null) {
			throw new SuperBootException("lhq-superboot-user-0015");
		}
		return userService.getUserByUserId((String) userId);
	}

}
