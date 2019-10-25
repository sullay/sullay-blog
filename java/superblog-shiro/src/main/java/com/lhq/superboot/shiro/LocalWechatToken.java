package com.lhq.superboot.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.lhq.superboot.enums.LoginType;

/**
 * @Description: shiro 重写wechat免登录 ，重写userIdPasswordToken 类
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public class LocalWechatToken extends UsernamePasswordToken {

	private static final long serialVersionUID = -7069449148037793017L;

	private LoginType type;
	
	private String realPwd;

	public LocalWechatToken() {
        super();
    }

	public LocalWechatToken(String userId, String password, LoginType type, boolean rememberMe, String host) {
        super(userId, password, rememberMe,  host);
        this.type = type;
    }

	/**免密登录*/
    public LocalWechatToken(String userId) {
        super(userId, "", false, null);
        this.type = LoginType.NOPASSWD;
    }

	/**账号密码登录*/
    public LocalWechatToken(String userId, String pwd, String realPwd) {
        super(userId, pwd, false, null);
        this.type = LoginType.PASSWORD;
        this.setRealPwd(realPwd);
    }

	public LoginType getType() {
		return type;
	}

	public void setType(LoginType type) {
		this.type = type;
	}

	public String getRealPwd() {
		return realPwd;
	}

	public void setRealPwd(String realPwd) {
		this.realPwd = realPwd;
	}
}
