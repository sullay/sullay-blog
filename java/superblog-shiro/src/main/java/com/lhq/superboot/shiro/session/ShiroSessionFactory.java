package com.lhq.superboot.shiro.session;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;

import com.lhq.superboot.util.IPUtils;

/**
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
public class ShiroSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext initData) {
        ShiroSession session = new ShiroSession();
        HttpServletRequest request = (HttpServletRequest)initData.get(DefaultWebSessionContext.class.getName() + ".SERVLET_REQUEST");
        session.setHost(IPUtils.getIpAddr(request));
        return session;
    }
}
