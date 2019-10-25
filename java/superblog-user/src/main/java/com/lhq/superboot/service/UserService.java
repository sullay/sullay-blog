package com.lhq.superboot.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.lhq.superboot.domain.Menu;
import com.lhq.superboot.domain.User;
import com.lhq.superboot.domain.UserRole;
import com.lhq.superboot.qo.PcLoginQo;
import com.lhq.superboot.qo.UserRegisterQo;

/**
 * @Description: 用户接口
 *
 * @author: lihaoqi
 * @date: 2019年5月19日
 * @version: 1.0.0
 */
public interface UserService {

	/**
	 * @Description: 通过用户Id查询用户和用户角色
	 *
	 * @param userId
	 * @return
	 */
	public UserRole getUserRoleByUserId(String userId);

	/**
	 * @Description: 通过用户名和渠道为PC/HT获取用户信息
	 *
	 * @param pcLoginQo
	 * @return
	 */
	public UserRole getUserByUsername(PcLoginQo pcLoginQo);

	/**
	 * @Description: 获取当前登录用户Id
	 *
	 * @return String
	 */
	public String getCurrentUserId();

	/**
	 * @Description: 获取当前登录用户
	 *
	 * @return User
	 */
	public User getCurrentUser();
	

	/**
	 * @Description: 通过用户id获取用户
	 *
	 * @param userId
	 * @return
	 */
	public User getUserByUserId(String userId);

	/**
	 * @Description: 更新密码
	 *
	 * @param newPassword
	 * @return
	 */
	public boolean updatePwd(String newPassword);

	/**
	 * @Description: 查询用户
	 *
	 * @return
	 */
	public List<Menu> getMenuByUser();

	/**
	 * @Description: 分页查询用户信息
	 *
	 * @param phone
	 * @param userName
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<UserRole> selectUserByPage(String phone, String userName, int pageNum, int pageSize);
	
	/**
	 * @Description: 用户授权
	 *
	 * @param users
	 * @param roleId
	 * @return
	 */
	public boolean updateUserRole(List<String> users, String roleId);

	/**
	 * @Description: 注册用户(可pc或ht)
	 *
	 * @param userRegisterQo
	 * @return
	 */
	public String registerUser(UserRegisterQo userRegisterQo);


	/**
	 * @Description: 根据渠道验证用户名是否存在
	 *
	 * @param userName
	 * @param channelFlg
	 * @return
	 */
	public boolean checkUsername(String userName, String channelFlg);

	/**
	 * @Description: 根据渠道验证手机号是否存在
	 *
	 * @param phone
	 * @param channelFlg
	 * @return
	 */
	public boolean checkPhone(String phone, String channelFlg);
	
	/**
	 * @Description: 通过手机修改用户密码
	 *
	 * @param phone
	 * @param password
	 * @param channelFlg
	 * @return
	 */
	public boolean updatePwdByPhone(String phone, String password, String channelFlg);


	/**
	 * @Description: 更新当前用户的手机
	 *
	 * @param phone
	 * @return
	 */
	public boolean updatePhoneByCurrentUser(String phone);
	
	/**
	 * @Description: 禁用或解禁用户
	 *
	 * @param userIdList
	 * @param isEnabled
	 */
	public void disableUser(List<String> userIdList, Integer isEnabled);
	
	/**
	 * @Description: 注销用户
	 *
	 * @param userIdList
	 */
	public void deleteUser(List<String> userIdList);

	/**
	 * @Description: 校验密码
	 * 
	 * @param oldPassword
	 * @return
	 */
	public boolean validPwd(String oldPassword);


}
