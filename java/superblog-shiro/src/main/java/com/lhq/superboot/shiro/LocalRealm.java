package com.lhq.superboot.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lhq.superboot.domain.Resource;
import com.lhq.superboot.domain.ResourceExample;
import com.lhq.superboot.domain.UserRole;
import com.lhq.superboot.enums.LoginSource;
import com.lhq.superboot.enums.LoginType;
import com.lhq.superboot.mapper.ResourceMapper;
import com.lhq.superboot.repository.ResRepository;
import com.lhq.superboot.repository.UserRepository;

/**
 * @Description: 实现AuthorizingRealm接口用户用户认证
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public class LocalRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(LocalRealm.class);
	
	/** 微信默认权限名称 **/
    private static final String WECHAT_DEFAULT_ROLE_NAME = "WechatDefault";

	@Autowired
	private ResRepository resRepository;
	
	@Autowired
	private ResourceMapper resMapper;
	
	@Autowired
	private UserRepository userRepository;

	/**
	 * @Description: 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("[Shiro] -> 授权 doGetAuthorizationInfo()");
		
		String userId = (String) principals.getPrimaryPrincipal();
		UserRole user = userRepository.selectUserAndRoleByUserId(userId);
		if (user == null) {
			logger.error("无法找到ID为" + userId + "的用户");
			return null;
		}
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		List<Resource> resList;

		if (LoginSource.XCX.name().equals(user.getChannel().getChannelFlg())) {
			// 默认给小程序用户一个权限
			authorizationInfo.addRole(WECHAT_DEFAULT_ROLE_NAME);
			
			ResourceExample resouce = new ResourceExample();
			resouce.createCriteria().andResChannelEqualTo(LoginSource.XCX.name());
			resList = resMapper.selectByExample(resouce);
			
			if (!resList.isEmpty()) {
				for (Resource res : resList) {
					authorizationInfo.addStringPermission(res.getResCode());
				}
			}
		} else {
			// 目前一个用户只有一个角色
			authorizationInfo.addRole(user.getRole().getRoleKey());
			resList = resRepository.selectResByRoleId(user.getRole().getRoleId());
			if (!resList.isEmpty()) {
				for (Resource res : resList) {
					// 小程序的资源权限不需要排除，因为存在pc端用户可以访问小程序用户接口
					//if (LoginSource.XCX.name().equals(res.getResChannel())) {continue;}
					authorizationInfo.addStringPermission(res.getResCode());
				}
			}
			
		}
		logger.debug("用户" + user.getUserName() + "具有的角色:" + authorizationInfo.getRoles());
		logger.debug("用户" + user.getUserName() + "具有的资源权限：" + authorizationInfo.getStringPermissions());
		return authorizationInfo;
	}
	
	/**
	 * @Description: 认证
	 * 	加密撒盐:ByteSource credentialsSalt = ByteSource.Util.bytes(userId);
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if (!(token instanceof LocalWechatToken)) {
			return null;
		}
		LocalWechatToken localWechatToken = (LocalWechatToken) token;
		if (token.getPrincipal() == null) {
			return null;
		}
		
		if (localWechatToken.getType().equals(LoginType.NOPASSWD)) {
			logger.info("[Shiro] -> 微信认证 doGetAuthenticationInfo()");
			return new SimpleAuthenticationInfo(localWechatToken.getPrincipal(), "" , getName());
		} else {
			logger.info("[Shiro] -> pc/后台认证 doGetAuthenticationInfo()");
			return new SimpleAuthenticationInfo(localWechatToken.getPrincipal(), localWechatToken.getRealPwd(), getName());
		}
	}

	/**
	 * @Description: 重写方法,清除当前用户的的 授权缓存
	 * 
	 * @param principals
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * @Description: 重写方法，清除当前用户的 认证缓存
	 * 
	 * @param principals
	 */
	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	/**
	 * @Description: 自定义方法：清除所有 授权缓存
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	/**
	 * @Description: 自定义方法：清除所有 认证缓存
	 */
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	/**
	 * @Description: 自定义方法：清除所有的 认证缓存 和 授权缓存
	 */
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}
