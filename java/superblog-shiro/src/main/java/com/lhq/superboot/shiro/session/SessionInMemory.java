package com.lhq.superboot.shiro.session;

import java.util.Date;

import org.apache.shiro.session.Session;

/**
 * @Description: 为了解决一次请求频繁访问redis读取session的解决方案,基于本地cache,如果是在一秒内的请求,都会从本地cache中获取request
 * 
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public class SessionInMemory {
	private Session session;
	private Date createTime;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
