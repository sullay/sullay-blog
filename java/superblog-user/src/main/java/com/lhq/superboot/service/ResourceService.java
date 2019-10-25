package com.lhq.superboot.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.lhq.superboot.domain.Resource;

/**
 * @Description: 资源信息
 *
 * @author: lihaoqi
 * @date: 2019年5月6日
 * @version: 1.0.0
 */
public interface ResourceService {

	/**
	 * @Description: 分页获取资源列表
	 *
	 * @param resName
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Resource> selectResourceByPage(String resName, String resUrl, int pageNum, int pageSize);
	
	/**
	 * @Description: 创建一个资源
	 *
	 * @param res
	 */
	public void createResource(Resource res);
	
	/**
	 * @Description: 修改资源信息
	 *
	 * @param res
	 */
	public void updateResource(Resource res);
	
	/**
	 * @Description: 禁用资源
	 *
	 * @param resIdList
	 * @param isEnabled
	 */
	public void disableResource(List<String> resIdList, Integer isEnabled);
	
	/**
	 * @Description: 删除资源
	 *
	 * @param resIdList
	 */
	public void deleteResource(List<String> resIdList);
	
	/**
	 * @Description: 获取某个角色下的资源
	 *
	 * @param roleId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<Resource> selectRoleRes(String roleId, int pageNum, int pageSize);
	
	/**
	 * @Description: 新增一个/多个资源权限对应关系
	 *
	 * @param roleId
	 * @param resIdList
	 */
	public void createRoleRes(String roleId, List<String> resIdList);
	
	/**
	 * @Description: 禁用资源权限对应关系
	 *
	 * @param roleResIdList
	 * @param isEnabled
	 */
	public void disableRoleRes(List<String> roleResIdList, Integer isEnabled);
	
	/**
	 * @Description: 删除资源权限对应关系
	 *
	 * @param roleResIdList
	 */
	public void deleteRoleRes(List<String> roleResIdList);
	
	/**
	 * @Description: 删除资源权限对应关系通过roleid与resid
	 * 
	 * @param roleId
	 * @param resIdList
	 */
	public void deleteRoleRes(String roleId, List<String> resIdList);
}
