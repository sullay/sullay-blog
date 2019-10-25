package com.lhq.superboot.service;

import java.util.Map;

import com.lhq.superboot.domain.User;

/**
 * @Description 对外接口api的key与secret
 *
 * @author: lihaoqi 
 * @date: 2019年7月24日 上午10:24:33 
 * @version: v1.0.0
 */
public interface UserApiService {

	/**
	 * @Description: 获取当前登录人对外接口key
	 *
	 * @param userId
	 * @return
	 */
	public String getApiKey();
	
	/**
	 * @Description: 通过用户id获取对外接口key
	 *
	 * @param userId
	 * @return
	 */
	public String getApiKey(String userId);
	
	/**
	 * @Description: 获取对外接口秘钥
	 *
	 * @param appKey
	 * @return
	 */
	public String getApiSecret(String appKey);
	
	/**
	 * @Description: 生成api签名，校验参数
	 *
	 * @param param
	 * @param appKey
	 * @return
	 */
	public String createApiSign(Map<String, String> param, String appKey);
	
	
	/**
	 * @Description: 通过apikey获取用户信息
	 *
	 * @param appKey
	 * @return
	 */
	public User getUserByApikey(String appKey);
}
