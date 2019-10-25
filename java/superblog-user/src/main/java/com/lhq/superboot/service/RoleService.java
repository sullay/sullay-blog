package com.lhq.superboot.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.lhq.superboot.domain.Role;

/**
 * @Description: 权限相关接口
 *
 * @author: lihaoqi
 * @date: 2019年4月23日
 * @version: 1.0.0
 */
public interface RoleService {

	/**
	 * @Description: 分页获取权限列表除PC外
	 *
	 * @param roleName
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Role> selectRoleByPage(String roleName, boolean isContainPC, int pageNum, int pageSize);
	
	/**
	 * @Description: 创建一个权限
	 *
	 * @param roleName
	 * @param roleKey
	 * @param roleSort
	 * @param dataScope
	 */
	public void createRole(String roleName, String roleKey, Integer roleSort, Integer dataScope);
	
	/**
	 * @Description: 修改权限内容
	 *
	 * @param role
	 */
	public void updateRole(Role role);
	
	/**
	 * @Description: 禁用一个/多个权限
	 *
	 * @param roleIdList
	 * @param isEnabled
	 */
	public void disableRole(List<String> roleIdList, Integer isEnabled);
	
	 /**
	 * @Description: 删除一个/多个权限
	 *
	 * @param roleIdList
	 */
	public void deleteRole(List<String> roleIdList);

}
