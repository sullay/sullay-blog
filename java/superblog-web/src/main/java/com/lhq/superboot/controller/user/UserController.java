package com.lhq.superboot.controller.user;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.lhq.superboot.domain.UserRole;
import com.lhq.superboot.enums.ConstEnumUtils.IS_ENABLED;
import com.lhq.superboot.enums.LoginSource;
import com.lhq.superboot.enums.UserMsg;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.qo.FindPwdQo;
import com.lhq.superboot.qo.UserRegisterQo;
import com.lhq.superboot.qo.UserUpdPwdQo;
import com.lhq.superboot.service.PhoneService;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.util.AESUtils;
import com.lhq.superboot.util.CheckUtils;
import com.lhq.superboot.vo.UserMenuVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 用户接口
 *
 * @author: lihaoqi
 * @date: 2019年4月19日
 * @version: 1.0.0
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
@Api(value = "用户controller", tags = { "用户操作接口" })
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PhoneService phoneService;
	

	/**
	 * @Description: 获取pc/ht的用户
	 * 
	 * @return
	 */
	@ApiOperation(value="获取当前用户详情", notes="获取当前用户详情")
	@GetMapping("/getCurrentUser")
	public Object getCurrentUser() {
		return userService.getCurrentUser();
	}


	/**
	 * @Description: 获取用户菜单
	 * 
	 * @return
	 */
	@GetMapping("/getUserMenu")
	public Object getUserMenu() {
		return UserMenuVo.convert(userService.getMenuByUser());
	}

	
	/**
	 * @Description 修改密码
	 * 
	 * @param userUpdPwdQo
	 * @return
	 */
	@ApiOperation(value="修改密码" ,notes="后台修改密码")
    @ApiImplicitParam(name = "userUpdPwdQo", value = "修改密码参数", required = true, paramType = "body", dataType = "UserUpdPwdQo")
	@PostMapping("/updatePwd")
	public Object updatePwd(@RequestBody UserUpdPwdQo userUpdPwdQo) {
		String newPassword = userUpdPwdQo.getNewPassword();
		String oldPassword = userUpdPwdQo.getOldPassword();
		String comfrimPassword = userUpdPwdQo.getComfrimPassword();
		
		if (StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(oldPassword)) {
			throw new SuperBootException("lhq-superboot-user-0002");
		}
		if (!newPassword.equals(comfrimPassword)) {
			throw new SuperBootException("lhq-superboot-user-0003");
		}
		if (!userService.validPwd(oldPassword)) {
			throw new SuperBootException("lhq-superboot-user-0004");
		}
		userService.updatePwd(newPassword);

		return UserMsg.MODIFYPWDSUCCESS.getCode();
	}

	
	/**
	 * @Description 注册PC用户
	 * 
	 * @param userRegisterQo
	 * @return
	 */
	@PostMapping("/pc/register")
	public Object registerPcUser(@RequestBody UserRegisterQo userRegisterQo) {
		if (StringUtils.isEmpty(userRegisterQo.getPhone())) {
			throw new SuperBootException("lhq-superboot-phone-0008");
		}

		if (StringUtils.isEmpty(userRegisterQo.getUserName())) {
			throw new SuperBootException("lhq-superboot-user-0009");
		}
		
		if (StringUtils.isEmpty(userRegisterQo.getChannelFlg())) {
			throw new SuperBootException("lhq-superboot-user-0010");
		}
		
		// 该接口只允许注册pc的用户，无法注册ht用户
		if (!userRegisterQo.getChannelFlg().equals(LoginSource.PC.name())) {
			throw new SuperBootException("lhq-superboot-user-0011");
		}

		if (userService.checkUsername(userRegisterQo.getUserName(), userRegisterQo.getChannelFlg())) {
			throw new SuperBootException("lhq-superboot-user-0013");
		}

		if (!CheckUtils.isPhoneLegal(userRegisterQo.getPhone())) {
			throw new SuperBootException("lhq-superboot-phone-0007");
		}
		
		if (userService.checkPhone(userRegisterQo.getPhone(), userRegisterQo.getChannelFlg())) {
			throw new SuperBootException("lhq-superboot-phone-0005");
		}

		if (StringUtils.isEmpty(userRegisterQo.getPassword())) {
			throw new SuperBootException("lhq-superboot-user-0002");
		}

		return userService.registerUser(userRegisterQo);
	}
	
	/**
	 * @Description 注册HT用户(超级管理员(token)创建普通管理员)
	 * 
	 * @param userRegisterQo
	 * @return
	 */
	@PostMapping("/ht/register")
	public Object registerHtUser(@RequestBody UserRegisterQo userRegisterQo) {
		if (StringUtils.isEmpty(userRegisterQo.getPhone())) {
			throw new SuperBootException("lhq-superboot-phone-0008");
		}

		if (StringUtils.isEmpty(userRegisterQo.getUserName())) {
			throw new SuperBootException("lhq-superboot-user-0009");
		}
		
		if (StringUtils.isEmpty(userRegisterQo.getChannelFlg())) {
			throw new SuperBootException("lhq-superboot-user-0010");
		}
		
		// 该接口只允许注册ht的用户，无法注册pc用户
		if (!userRegisterQo.getChannelFlg().equals(LoginSource.HT.name())) {
			throw new SuperBootException("lhq-superboot-user-0011");
		}

		if (userService.checkUsername(userRegisterQo.getUserName(), userRegisterQo.getChannelFlg())) {
			throw new SuperBootException("lhq-superboot-user-0013");
		}
		
		if (!CheckUtils.isPhoneLegal(userRegisterQo.getPhone())) {
			throw new SuperBootException("lhq-superboot-phone-0007");
		}

		if (userService.checkPhone(userRegisterQo.getPhone(), userRegisterQo.getChannelFlg())) {
			throw new SuperBootException("lhq-superboot-phone-0005");
		}

		if (StringUtils.isEmpty(userRegisterQo.getPassword())) {
			throw new SuperBootException("lhq-superboot-user-0002");
		}
		
		// 校验密码是否过于简单
		if (!CheckUtils.checkPassword(AESUtils.decrypt(userRegisterQo.getOriginalPwd()))) {
			throw new SuperBootException("lhq-superboot-user-0014");
		}

		return userService.registerUser(userRegisterQo);
	}

	
	/**
	 * @Description 找到密码
	 * 
	 * @param findPwdQo
	 * @return
	 */
	@PostMapping("/findPassword")
	public Object findPassword(@RequestBody FindPwdQo findPwdQo) {
		String token = findPwdQo.getToken();
		String password = findPwdQo.getPassword();
		if (StringUtils.isEmpty(token)) {
			throw new SuperBootException("lhq-superboot-user-0017");
		}
		if (StringUtils.isEmpty(password)) {
			throw new SuperBootException("lhq-superboot-user-0002");
		}
		// 权限认证
		if (!phoneService.validatePhoneRandom(token)) {
			throw new SuperBootException("lhq-superboot-role-0006");
		}
		String phone = new String(Base64.getDecoder().decode(token)).split("#")[1];
		// 修改密码
		userService.updatePwdByPhone(phone, password, LoginSource.PC.name());

		return UserMsg.MODIFYPWDSUCCESS.getCode();
	}
	
	/**
	 * @Description 获取用户列表（token权限）
	 * 
	 * @param phone
	 * @param userName
	 * @param page
	 * @return
	 */
	@GetMapping("/getUserList")
	public Object getUserList(String phone, String userName, PageInfo<UserRole> page) {
		Page<UserRole> userPage = userService.selectUserByPage(phone, userName, page.getPageNum(), page.getPageSize());
		page.setList(userPage.getResult());
		page.setTotal(userPage.getTotal());
		return page;
	}
	
	/**
	 * @Description: 禁用用户（批量）
	 * 
	 * @param userIdList
	 * @return
	 */
	@PostMapping("/disable")
	public Object batchDisableUser(@RequestBody List<String> userIdList) {
		if (userIdList == null || userIdList.isEmpty()) {
			throw new SuperBootException("lhq-superboot-user-0007");
		}
		userService.disableUser(userIdList, IS_ENABLED.NO.value());
		return UserMsg.MODIFYMESSSUCCESS.getCode();
	}
	
	/**
	 * @Description: 解禁用户（批量）
	 * 
	 * @param userIdList
	 * @return
	 */
	@PostMapping("/enable")
	public Object batchEnableUser(@RequestBody List<String> userIdList) {
		if (userIdList == null || userIdList.isEmpty()) {
			throw new SuperBootException("lhq-superboot-user-0007");
		}
		userService.disableUser(userIdList, IS_ENABLED.YES.value());
		return UserMsg.MODIFYMESSSUCCESS.getCode();
	}
	
	/**
	 * @Description: 销毁用户（批量）
	 * 
	 * @param userIdList
	 * @return
	 */
	@PostMapping("/delele")
	public Object batchDeleleUser(@RequestBody List<String> userIdList) {		
		if (userIdList == null || userIdList.isEmpty()) {
			throw new SuperBootException("lhq-superboot-user-0007");
		}
		userService.deleteUser(userIdList);
		return UserMsg.MODIFYMESSSUCCESS.getCode();
	}

}
