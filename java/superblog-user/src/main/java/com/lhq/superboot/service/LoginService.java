package com.lhq.superboot.service;

import com.lhq.superboot.qo.PcLoginQo;

/**
 * @Description: login 接口
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public interface LoginService {

	/**
	 * @Description: pc或者后台登录
	 *
	 * @param pcLoginQo
	 * @return
	 */
	public String userLogin(PcLoginQo pcLoginQo);
	
}
