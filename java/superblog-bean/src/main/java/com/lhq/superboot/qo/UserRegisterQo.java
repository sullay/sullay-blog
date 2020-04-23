package com.lhq.superboot.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户注册qo
 *
 * @author: lihaoqi
 * @date: 2019年4月23日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterQo {

	private String userName;
	private String companyCode;
	private String phone;
	private String password;
	private String email;
	private String channelFlg;
	private String validCode;
	private String originalPwd;
}
