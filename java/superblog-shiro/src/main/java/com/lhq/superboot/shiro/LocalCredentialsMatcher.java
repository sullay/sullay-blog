package com.lhq.superboot.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lhq.superboot.domain.User;
import com.lhq.superboot.domain.UserExample;
import com.lhq.superboot.enums.LoginType;
import com.lhq.superboot.enums.ConstEnumUtils.IS_ENABLED;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.mapper.UserMapper;
import com.lhq.superboot.shiro.property.ShiroConfigProperty;
import com.lhq.superboot.util.RedisUtils;

/**
 * @Description: 重写密码校验器(微信免登及登录限制)
 * 
 * @version: 1.0.0
 * @author: lihaoqi
 * @date: 2019年4月19日
 */
public class LocalCredentialsMatcher extends HashedCredentialsMatcher {

	private static final Logger logger = LoggerFactory.getLogger(LocalCredentialsMatcher.class);
	
	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private ShiroConfigProperty shiroProp;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		// 微信免密登录
		if (authcToken instanceof LocalWechatToken) {
			LocalWechatToken tk = (LocalWechatToken) authcToken;
			if (tk.getType().equals(LoginType.NOPASSWD)) {
				return true;
			}
		}

		// 登录限制
		String userId = (String) authcToken.getPrincipal();
		String accountlockedKey = shiroProp.getAccountlockPrefix() + userId;
		String repeatlockedKey = shiroProp.getRepeatlockPrefix() + userId;
		
		AtomicInteger loginCount = (AtomicInteger) redisUtils.get(accountlockedKey);
		// 不存在，创建一个
		if (loginCount == null) {
			loginCount = new AtomicInteger(0);
		}
		int curCount = loginCount.getAndIncrement();
		// 大于锁账号的次数锁定账号
		if(curCount > shiroProp.getAccountlockCount()) {
			lockedAccount(userId);
			redisUtils.del(accountlockedKey);
			logger.info("锁定用户,并将用户登录次数缓存清除" + userId);
			throw new LockedAccountException();
		}
		// 重复登录()失败,增加redis重复锁
		if (curCount > shiroProp.getRepeatlockCount()) {
			if (null != redisUtils.get(repeatlockedKey)) {
				logger.info("多次登录用户" + userId);
				throw new SuperBootException("lhq-superboot-user-0022");
			}
		}

		boolean matches = super.doCredentialsMatch(authcToken, info);
		
		if (matches) {
			redisUtils.del(accountlockedKey);
			//清空session,让cookie过期
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			request.getSession().invalidate();
			if (request.getCookies() != null) {
				for (Cookie cookie : request.getCookies()) {
	    			if (cookie.getName().equals(shiroProp.getSessionIdName())) {
	    				cookie.setValue(null);  
	    				cookie.setMaxAge(0);
	    			}
	    		}
			}
		} else {
			redisUtils.set(accountlockedKey, loginCount, shiroProp.getAccountlockTime());
			if (loginCount.get() > shiroProp.getRepeatlockCount() && loginCount.get() <= shiroProp.getAccountlockCount()) {
				redisUtils.set(repeatlockedKey, loginCount, shiroProp.getRepeatlockTime());
			}
		}
		
		return matches;
	}

	/**
	 * @Description: 锁定账号
	 */
	private void lockedAccount(String userId) {
		User user = new User().toBuilder().isEnabled(IS_ENABLED.NO.value()).build();
		UserExample userExample = new UserExample();
		userExample.createCriteria().andUserIdEqualTo(userId);
		userMapper.updateByExampleSelective(user, userExample);
	}
	
}
