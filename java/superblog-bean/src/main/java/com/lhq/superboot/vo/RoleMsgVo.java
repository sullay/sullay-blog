package com.lhq.superboot.vo;

import com.lhq.superboot.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 权限信息dto
 *
 * @author: lihaoqi
 * @date: 2019年4月23日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleMsgVo {

	private String roleId;
	private String roleName;
	private String roleKey;
	private int roleSort;
	
	public static RoleMsgVo convert(Role role) {
		RoleMsgVo roleMsg = new RoleMsgVo().toBuilder().roleId(role.getRoleId()).roleName(role.getRoleName())
				.roleKey(role.getRoleKey()).roleSort(role.getRoleSort()).build();
		return roleMsg;
	}
}
