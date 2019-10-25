package com.lhq.superboot.controller.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lhq.superboot.enums.LoginMsg;
import com.lhq.superboot.qo.PcLoginQo;
import com.lhq.superboot.service.LoginService;
import com.lhq.superboot.util.ResultUtils;
import com.lhq.superboot.util.StringUtils;

import io.swagger.annotations.Api;

/**
 * @Description: 用户登录controller 不被切面处理，需自行处理返回json结果集
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@Api(value = "登录controller", tags = { "登录操作接口" })
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * @Description PC HT登录
     * 
     * @param pcLoginQo
     * @return
     */
    @PostMapping(value = "/user/login")
    public String login(@RequestBody PcLoginQo pcLoginQo) {
        if (StringUtils.isEmpty(pcLoginQo.getUserName())) {
            return ResultUtils.error(HttpStatus.BAD_REQUEST.value(), LoginMsg.EMPTYACCOUNT.getCode());
        }
        if (StringUtils.isEmpty(pcLoginQo.getPassword())) {
            return ResultUtils.error(HttpStatus.BAD_REQUEST.value(), LoginMsg.EMPTYPASSWORD.getCode());
        }
        if (StringUtils.isEmpty(pcLoginQo.getChannelFlg())) {
            return ResultUtils.error(HttpStatus.BAD_REQUEST.value(), LoginMsg.UNKNOWNSOURCE.getCode());
        }
        return loginService.userLogin(pcLoginQo);
    }

    /**
     * @Description: 登出
     */
    @GetMapping(value = "/user/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtils.error(HttpStatus.OK.value(), LoginMsg.LOGOUTSUCCESS.getCode());
    }

    /**
     * @Description: 未授权（废弃）
     */
    @GetMapping(value = "/unauthorized")
    public String unauthorized() {
        return ResultUtils.error(HttpStatus.FORBIDDEN.value(), LoginMsg.UNAUTH.getCode());
    }

    /**
     * @Description: 未登录（废弃）
     */
    @GetMapping(value = "/unlogin")
    public String unlogin() {
        return ResultUtils.error(HttpStatus.UNAUTHORIZED.value(), LoginMsg.UNLOGIN.getCode());
    }

}
