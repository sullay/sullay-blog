package com.lhq.superboot.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.lhq.superboot.domain.Menu;

/**
 * @Description: 菜单相关接口
 * 
 * @author: lihaoqi
 * @date: 2019年5月6日
 * @version: 1.0.0
 */
public interface MenuService {

	/**
	 * @Description: 分页获取菜单列表
	 *
	 * @param menuName
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Menu> selectMenuByPage(String menuName, int pageNum, int pageSize);
	
	/**
	 * @Description: 创建一个菜单
	 *
	 * @param menu
	 */
	public void createMenu(Menu menu);
	
	/**
	 * @Description: 修改菜单信息
	 *
	 * @param menu
	 */
	public void updateMenu(Menu menu);
	
	/**
	 * @Description: 禁用菜单
	 *
	 * @param menuIdList
	 * @param isEnabled
	 */
	public void disableMenu(List<String> menuIdList, Integer isEnabled);
	
	/**
	 * @Description: 删除菜单
	 *
	 * @param menuIdList
	 */
	public void deleteMenu(List<String> menuIdList);
	
	/**
	 * @Description: 获取对应权限所有的菜单列表
	 *
	 * @param roleId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Menu> selectRoleMenu(String roleId, int pageNum, int pageSize);
	
	/**
	 * @Description: 新增菜单权限对应关系
	 *
	 * @param roleId
	 * @param menuIdList
	 */
	public void createRoleMenu(String roleId, List<String> menuIdList);
	
	/**
	 * @Description: 禁用菜单权限对应关系
	 *
	 * @param roleMenuIdList
	 * @param isEnabled
	 */
	public void disableRoleMenu(List<String> roleMenuIdList, Integer isEnabled);
	
	/**
	 * @Description: 删除菜单权限对应关系
	 *
	 * @param roleMenuIdList
	 */
	public void deleteRoleMenu(List<String> roleMenuIdList);
}
