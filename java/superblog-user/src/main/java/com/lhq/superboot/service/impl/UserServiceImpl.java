package com.lhq.superboot.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhq.superboot.domain.*;
import com.lhq.superboot.enums.ConstEnumUtils.IS_DELETE;
import com.lhq.superboot.enums.LoginSource;
import com.lhq.superboot.enums.UserMsg;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.mapper.ChannelMapper;
import com.lhq.superboot.mapper.UserInfoMapper;
import com.lhq.superboot.mapper.UserMapper;
import com.lhq.superboot.qo.PcLoginQo;
import com.lhq.superboot.qo.UserRegisterQo;
import com.lhq.superboot.repository.ResRepository;
import com.lhq.superboot.repository.UserRepository;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.shiro.LocalRealm;
import com.lhq.superboot.shiro.utils.ShiroMd5Util;
import com.lhq.superboot.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description: 用户实现类
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ChannelMapper channelMapper;

	@Autowired
	private ResRepository resRepository;

	@Autowired
	private LocalRealm localRealm;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public UserRole getUserRoleByUserId(String userId) {
		return userRepository.selectUserAndRoleByUserId(userId);
	}

	@Override
	public UserRole getUserByUsername(PcLoginQo pcLoginQo) {
		Map<String, Object> param = new HashMap<>();
		param.put("userName", pcLoginQo.getUserName());
		param.put("channelFlg", pcLoginQo.getChannelFlg());
		return userRepository.selectUserAndRoleByUsername(param);
	}

	@Override
	public UserRole getUserByEmail(PcLoginQo pcLoginQo) {
		Map<String, Object> param = new HashMap<>();
		param.put("email", pcLoginQo.getUserName());
		param.put("channelFlg", pcLoginQo.getChannelFlg());
		return userRepository.selectUserAndRoleByEmail(param);
	}

	@Override
	public String getCurrentUserId() {
		try {
			String userId = (String) SecurityUtils.getSubject().getPrincipal();
			if (StringUtils.isEmpty(userId)) {
				return null;
			}
			return userId;
		} catch (Exception e) {
			logger.error("获取用户id失败，原因：{}", e.getMessage());
			return null;
		}
	}
	
	@Override
	public User getCurrentUser() {
		String userId = getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		return userMapper.selectByPrimaryKey(userId);
	}


	@Override
	public User getUserByUserId(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	@Transactional
	public boolean updatePwd(String newPassword) {
		String userId = getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			throw new SuperBootException("lhq-superboot-user-0021");
		}

		User user = new User().toBuilder().password(ShiroMd5Util.PwdMd5(newPassword)).build();
		UserExample userCondition = new UserExample();
		userCondition.createCriteria().andUserIdEqualTo(userId);
		userMapper.updateByExampleSelective(user, userCondition);

		// 清除缓存数据
		Cache<Object, AuthenticationInfo> authenticationCache = localRealm.getAuthenticationCache();
		if (authenticationCache != null) {
			authenticationCache.remove(userId);
		}
		return true;
	}

	@Override
	public List<Menu> getMenuByUser() {
		String userId = getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			return null;
		}

		return resRepository.selectMenuByUserId(userId);
	}

	@Override
	public Page<UserRole> selectUserByPage(String phone, String userName, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("userName", userName);
		param.put("phone", phone);
		param.put("channel", Arrays.asList("HT"));
		Page<UserRole> userPage = (Page<UserRole>) userRepository.selectUserList(param);
		return userPage;
	}

	@Override
	@Transactional
	public boolean updateUserRole(List<String> users, String roleId) {
		// 修改用户权限
		// 清除修改用户的权限缓存数据
		Cache<Object, AuthorizationInfo> authorizationCache = localRealm.getAuthorizationCache();
		if (authorizationCache != null) {
			for (String id : users) {
				authorizationCache.remove(id);
			}
		}
		return true;
	}

	@Override
	@Transactional
	public String registerUser(UserRegisterQo userRegisterQo) {
		try {
			// 获取渠道信息
			Channel channel = getChannelMsg(userRegisterQo.getChannelFlg());

			String phone = userRegisterQo.getPhone();
			String userName = userRegisterQo.getUserName();
			String email = userRegisterQo.getEmail();

			// 插入用户信息表
			UserInfo userInfo = new UserInfo();
			userInfoMapper.insertSelective(userInfo);
			String userInfoId = userInfo.getUserInfoId();

			// 插入一条数据进入用户表
			User user = new User().toBuilder().userName(userName).phone(phone).email(email)
					.password(ShiroMd5Util.PwdMd5(userRegisterQo.getPassword())).channelId(channel.getChannelId())
					.userInfoId(userInfoId).build();
			userMapper.insertSelective(user);

			return UserMsg.REGISTERSUCCESS.getCode();
		} catch (SuperBootException e) {
			logger.error("获取渠道信息异常", e.getMessage());
			throw new SuperBootException("lhq-superboot-user-0011");
		} catch (Exception e) {
			logger.error("执行语句异常", e);
			throw new SuperBootException(e);
		}

	}

	@Override
	public boolean checkUsername(String userName, String channelFlg) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUserNameEqualTo(userName)
				.andChannelIdEqualTo(getChannelMsg(channelFlg).getChannelId());
		if (!userMapper.selectByExample(userExample).isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkPhone(String phone, String channelFlg) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPhoneEqualTo(phone)
				.andChannelIdEqualTo(getChannelMsg(channelFlg).getChannelId());
		if (!userMapper.selectByExample(userExample).isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updatePwdByPhone(String phone, String password, String channelFlg) {
		User user = new User().toBuilder().password(ShiroMd5Util.PwdMd5(password)).build();
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPhoneEqualTo(phone)
				.andChannelIdEqualTo(getChannelMsg(channelFlg).getChannelId());

		List<User> userList = userMapper.selectByExample(userExample);
		if (userList.isEmpty()) {
			logger.info("不存在手机号为{}的用户 ", phone);
			throw new SuperBootException("lhq-superboot-phone-0011", phone);
		}
		if (userList.size() >= 2) {
			logger.info("手机号为{}的用户存在两个 ", phone);
			throw new SuperBootException("lhq-superboot-phone-0012", phone);
		}
		userMapper.updateByExampleSelective(user, userExample);

		// 清除缓存数据
		Cache<Object, AuthenticationInfo> authenticationCache = localRealm.getAuthenticationCache();
		if (authenticationCache != null) {
			authenticationCache.remove(userList.get(0).getUserId());
		}

		return true;
	}


	@Override
	public boolean updatePhoneByCurrentUser(String phone) {
		String userId = getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			return false;
		}
		User user = new User().toBuilder().phone(phone).build();
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(userId);
		userMapper.updateByExampleSelective(user, userExample);
		return true;
	}


	@Override
	public void disableUser(List<String> userIdList, Integer isEnabled) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUserIdIn(userIdList);
		User user = new User().toBuilder().isEnabled(isEnabled).build();
		userMapper.updateByExampleSelective(user, userExample);
	}

	@Override
	public void deleteUser(List<String> userIdList) {
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUserIdIn(userIdList);
		User user = new User().toBuilder().isDeleted(IS_DELETE.YES.value()).build();
		userMapper.updateByExampleSelective(user, userExample);
	}

	@SuppressWarnings("unused")
	private List<Channel> getChannelMsg() {
		List<String> sourceList = new ArrayList<String>(2);
		sourceList.add(LoginSource.PC.name());
		sourceList.add(LoginSource.HT.name());

		ChannelExample channelExample = new ChannelExample();
		channelExample.createCriteria().andChannelFlgIn(sourceList);
		List<Channel> channel = channelMapper.selectByExample(channelExample);
		if (channel.isEmpty()) {
			logger.info("无法获取pc和后台的渠道信息 ");
			throw new SuperBootException("lhq-superboot-user-0011");
		}
		return channel;
	}

	private Channel getChannelMsg(String channelFlg) {
		ChannelExample channelExample = new ChannelExample();
		channelExample.createCriteria().andChannelFlgEqualTo(channelFlg);
		List<Channel> channel = channelMapper.selectByExample(channelExample);
		if (channel.isEmpty()) {
			logger.info("无法获取{}的渠道信息 ", channelFlg);
			throw new SuperBootException("lhq-superboot-user-0011");
		}
		return channel.get(0);
	}
	
	@Override
	public boolean validPwd(String oldPassword) {
		User user = getCurrentUser();

		if (ShiroMd5Util.PwdMd5(oldPassword).equals(user.getPassword())) {
			return true;
		}
		return false;
	}

}
