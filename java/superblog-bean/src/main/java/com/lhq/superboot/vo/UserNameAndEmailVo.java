package com.lhq.superboot.vo;

import com.lhq.superboot.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 用户菜单dto
 *
 * @author: lihaoqi
 * @date: 2019年4月23日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserNameAndEmailVo {

	private String userName;
	private String email;

	public static UserNameAndEmailVo convert(User user) {
		return new UserNameAndEmailVo().toBuilder().userName(user.getUserName()).email(user.getEmail()).build();
	}


}
