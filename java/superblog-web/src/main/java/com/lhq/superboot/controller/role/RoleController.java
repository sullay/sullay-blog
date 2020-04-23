package com.lhq.superboot.controller.role;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.lhq.superboot.domain.Role;
import com.lhq.superboot.enums.ConstEnumUtils.IS_ENABLED;
import com.lhq.superboot.enums.RoleMsg;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.qo.UserGrantQo;
import com.lhq.superboot.service.RoleService;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.vo.RoleMsgVo;

import io.swagger.annotations.Api;

/**
 * @Description: role Controller
 * 
 * @author: lihaoqi
 * @date: 2019年4月23日
 * @version: 1.0.0
 */
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
@Api(value = "角色权限controller", tags = { "角色权限操作接口" })
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * @Description 获取角色列表
     * 
     * @param roleName
     * @param page
     * @return
     */
    @GetMapping("/getRoles")
    public Object getRoles(String roleName, Boolean isContainPC, PageInfo<RoleMsgVo> page) {
        isContainPC = isContainPC == null ? false : isContainPC;
        Page<Role> rolePage = roleService.selectRoleByPage(roleName, isContainPC, page.getPageNum(),
                page.getPageSize());
        page.setList(rolePage.getResult().stream().map(role -> RoleMsgVo.convert(role)).collect(Collectors.toList()));
        page.setTotal(rolePage.getTotal());
        return page;
    }

    /**
     * @Description 授权用户(批量)
     * 
     * @param userGrantQo
     * @return
     */
    @PostMapping("/grantAuthUsers")
    public Object grantAuthUsers(@RequestBody UserGrantQo userGrantQo) {
        String roleId = userGrantQo.getRoleId();
        List<String> userList = userGrantQo.getUsers();
        if (StringUtils.isEmpty(roleId)) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }

        if (userList == null || userList.isEmpty()) {
            throw new SuperBootException("lhq-superboot-user-0008");
        }

        if (!userService.updateUserRole(userList, roleId)) {
            return RoleMsg.UPDATEERROR.getCode();
        }
        return RoleMsg.UPDATESUCCESS.getCode();
    }

    /**
     * @Description 创建角色
     * 
     * @param role
     * @return
     */
    @PostMapping("/role/create")
    public Object createRole(@RequestBody Role role) {
        String roleName = role.getRoleName();
        String roleKey = role.getRoleKey();
        Integer roleSort = role.getRoleSort();
        Integer dataScope = role.getDataScope();
        if (StringUtils.isEmpty(roleName)) {
            throw new SuperBootException("lhq-superboot-role-0002");
        }
        if (StringUtils.isEmpty(roleKey)) {
            throw new SuperBootException("lhq-superboot-role-0003");
        }
        if (null != roleSort) {
            throw new SuperBootException("lhq-superboot-role-0004");
        }
        if (null != dataScope) {
            throw new SuperBootException("lhq-superboot-role-0005");
        }

        roleService.createRole(roleName, roleKey, roleSort, dataScope);
        return RoleMsg.CREATEROLESUCCESS.getCode();
    }

    /**
     * @Description 更新角色信息
     * 
     * @param role
     * @return
     */
    @PostMapping("/role/update")
    public Object updateRole(@RequestBody Role role) {
        if (StringUtils.isEmpty(role.getRoleId())) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        roleService.updateRole(role);
        return RoleMsg.UPDATEROLESUCCESS.getCode();
    }

    /**
     * @Description 删除角色信息
     * 
     * @param role
     * @return
     */
    @PostMapping("/role/delete")
    public Object deleteRole(@RequestBody Role role) {
        if (StringUtils.isEmpty(role.getRoleId())) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        roleService.deleteRole(Arrays.asList(role.getRoleId()));
        return RoleMsg.UPDATEROLESUCCESS.getCode();
    }

    /**
     * @Description 批量删除角色信息
     * 
     * @param roleIdList
     * @return
     */
    @PostMapping("/role/batchDelete")
    public Object batchDeleteRole(@RequestBody List<String> roleIdList) {
        if (roleIdList == null || roleIdList.isEmpty()) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        roleService.deleteRole(roleIdList);
        return RoleMsg.UPDATEROLESUCCESS.getCode();
    }

    /**
     * @Description 禁用角色信息
     * 
     * @param role
     * @return
     */
    @PostMapping("/role/disable")
    public Object disableRole(@RequestBody Role role) {
        if (StringUtils.isEmpty(role.getRoleId())) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        roleService.disableRole(Arrays.asList(role.getRoleId()), IS_ENABLED.NO.value());
        return RoleMsg.UPDATEROLESUCCESS.getCode();
    }

    /**
     * @Description 批量禁用角色信息
     * 
     * @param roleIdList
     * @return
     */
    @PostMapping("/role/batchDisable")
    public Object batchDisableRole(@RequestBody List<String> roleIdList) {
        if (roleIdList == null || roleIdList.isEmpty()) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        roleService.disableRole(roleIdList, IS_ENABLED.NO.value());
        return RoleMsg.UPDATEROLESUCCESS.getCode();
    }

}
