package com.lhq.superboot.shiro;

import java.util.Iterator;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import com.lhq.superboot.enums.LoginType;

/**
 * @Description: 重写一下onFailedLogin方法，清除缓存
 * 
 * @author: lihaoqi
 * @date: 2019年4月20日
 * @version: 1.0.0
 */
public class LocalWebSecurityManager extends DefaultWebSecurityManager {

	@Override
	protected void onFailedLogin(AuthenticationToken token, AuthenticationException ae, Subject subject) {
		// 微信登录没有密码错误，不用清除缓存，防止别人通过登录失败恶意清除PC端用户缓存
		if (token instanceof LocalWechatToken) {
			LocalWechatToken localWechatToken = (LocalWechatToken) token;
			if (localWechatToken.getType().equals(LoginType.PASSWORD)) {
				String userId = (String) token.getPrincipal();
				Iterator<Realm> iterator = this.getRealms().iterator(); 
				while(iterator.hasNext()) {
					Realm realm =  iterator.next();
					if (realm instanceof LocalRealm) {
						LocalRealm localRealm = (LocalRealm) realm;
						localRealm.clearCachedAuthenticationInfo(new SimplePrincipalCollection(userId, userId));
					}
				}
			}
		} 
        super.onFailedLogin(token, ae, subject);
    }
}
