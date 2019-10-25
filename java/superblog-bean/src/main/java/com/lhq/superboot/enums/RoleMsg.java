package com.lhq.superboot.enums;

/**
 * @Description: 权限信息枚举
 *
 * @author: lihaoqi
 * @date: 2019年4月25日
 * @version: 1.0.0
 */
public enum RoleMsg {

	UPDATESUCCESS("修改用户权限成功"),

	UPDATEERROR("修改用户权限失败"),

	UPDATEROLESUCCESS("修改权限信息成功"),

	CREATEROLESUCCESS("创建权限信息成功"),

	UPDATERESSUCCESS("修改资源权限成功"),

	CREATERESSUCCESS("创建资源权限成功"),

	UPDATEMENUSUCCESS("修改菜单权限成功"),

	CREATEMENUSUCCESS("创建菜单权限成功");

	private String code;

	RoleMsg(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
