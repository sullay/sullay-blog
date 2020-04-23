package com.lhq.superboot.controller.login;

import com.lhq.superboot.enums.LoginSource;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.qo.UserRegisterQo;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.util.CheckUtils;
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

    @Autowired
    private UserService userService;

    /**
     * @Description PC登录
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
            pcLoginQo.setChannelFlg(LoginSource.PC.name());
        }
        return loginService.userLogin(pcLoginQo);
    }

    /**
     * @Description PC注册
     *
     * @param userRegisterQo
     * @return
     */
    @PostMapping(value = "/user/register")
    public String login(@RequestBody UserRegisterQo userRegisterQo) {
        if (StringUtils.isEmpty(userRegisterQo.getUserName())) {
            throw new SuperBootException("lhq-superboot-user-0009");
        }

        if (!CheckUtils.checkUsername(userRegisterQo.getUserName())) {
            throw new SuperBootException("lhq-superboot-user-0019");
        }

        if (StringUtils.isEmpty(userRegisterQo.getEmail()) || !CheckUtils.isEmailLegal(userRegisterQo.getEmail())) {
            throw new SuperBootException("lhq-superboot-user-0012");
        }


        if (StringUtils.isEmpty(userRegisterQo.getChannelFlg()) || !userRegisterQo.getChannelFlg().equals(LoginSource.PC.name())) {
            userRegisterQo.setChannelFlg(LoginSource.PC.name());
        }

        if (StringUtils.isNotEmpty(userRegisterQo.getPhone()) && !CheckUtils.isPhoneLegal(userRegisterQo.getPhone())) {
            throw new SuperBootException("lhq-superboot-phone-0007");
        }

        if (StringUtils.isEmpty(userRegisterQo.getPassword())) {
            throw new SuperBootException("lhq-superboot-user-0002");
        }

        return ResultUtils.success(userService.registerUser(userRegisterQo));
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

}
