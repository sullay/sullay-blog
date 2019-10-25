package com.lhq.superboot.qo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 找到密码qo
 *
 * @author: lihaoqi
 * @date: 2019年4月24日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FindPwdQo {
	
	private String phone;
	
	private String token;
	
	private String password;
	
}
