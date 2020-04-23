package com.lhq.superboot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户权限实体类
 *
 * @author: lihaoqi
 * @version: 1.0.0
 * @date: 2019年4月17日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends User {
	
	private Role role;

	private Channel channel;

}
