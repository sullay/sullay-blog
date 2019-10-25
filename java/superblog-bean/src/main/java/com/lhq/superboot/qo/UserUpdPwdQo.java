package com.lhq.superboot.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户更新密码qo
 *
 * @author: lihaoqi
 * @date: 2019年4月24日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdPwdQo {
	
	private String oldPassword;
	
	private String newPassword;
	
	private String comfrimPassword;
	
}
