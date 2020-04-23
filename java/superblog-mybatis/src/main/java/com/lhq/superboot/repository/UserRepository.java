package com.lhq.superboot.repository;

import java.util.List;
import java.util.Map;

import com.lhq.superboot.domain.UserRole;

/**
 * @Description: 用户数据访问层
 *
 * @author: lihaoqi
 * @date: 2019年5月19日
 * @version: 1.0.0
 */
public interface UserRepository {

	/**
	 * @Description: 查询用户和权限通过username
	 *
	 * @param param
	 * @return
	 */
	public UserRole selectUserAndRoleByUsername(Map<String, Object> param);

    /**
     * @Description: 查询用户和权限通过email
     *
     * @param param
     * @return
     */
    public UserRole selectUserAndRoleByEmail(Map<String, Object> param);

	/**
	 * @Description: 通过user_id获取用户与权限信息
	 *
	 * @param userId
	 * @return
	 */
	public UserRole selectUserAndRoleByUserId(String userId);


	/**
	 * @Description: 通过条件查询用户列表
	 *
	 * @param param
	 * @return
	 */
	public List<UserRole> selectUserList(Map<String, Object> param);


	/**
	 * @Description: 通过资源权限Id，获取那些用户与这条权限记录有关，从而删除这部分用户的资源权限缓存,获取用户userId
	 *
	 * @param roleResId
	 * @return
	 */
	public List<String> selectUserByRoleResId(String roleResId);
}
