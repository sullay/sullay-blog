package com.lhq.superboot.controller.role;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.lhq.superboot.domain.Menu;
import com.lhq.superboot.domain.RoleMenu;
import com.lhq.superboot.enums.ConstEnumUtils.IS_ENABLED;
import com.lhq.superboot.enums.RoleMsg;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.qo.BatchRoleMenuQo;
import com.lhq.superboot.service.MenuService;
import com.lhq.superboot.util.StringUtils;
import com.lhq.superboot.vo.MenuMsgVo;

import io.swagger.annotations.Api;

/**
 * @Description: 菜单权限
 *
 * @author: lihaoqi
 * @date: 2019年5月6日
 * @version: 1.0.0
 */
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
@Api(value = "菜单controller", tags = { "菜单操作接口" })
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * @Description 查询菜单列表
     * 
     * @param menuName
     * @param page
     * @return
     */
    @GetMapping("/menu/list")
    public Object queryMenus(String menuName, PageInfo<MenuMsgVo> page) {
        Page<Menu> menuPage = menuService.selectMenuByPage(menuName, page.getPageNum(), page.getPageSize());
        page.setList(menuPage.getResult().stream().map(menu -> MenuMsgVo.convert(menu)).collect(Collectors.toList()));
        page.setTotal(menuPage.getTotal());
        return page;
    }

    /**
     * @Description 创建菜单
     * 
     * @param menu
     * @return
     */
    @PostMapping("/menu/create")
    public Object createMenu(@RequestBody Menu menu) {
        if (StringUtils.isEmpty(menu.getChannelId())) {
            throw new SuperBootException("lhq-superboot-user-0010");
        }
        if (StringUtils.isEmpty(menu.getMenuName())) {
            throw new SuperBootException("lhq-superboot-menu-0001");
        }
        if (null == menu.getMenuSort()) {
            throw new SuperBootException("lhq-superboot-menu-0002");
        }
        menuService.createMenu(menu);
        return RoleMsg.CREATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 更新菜单
     * 
     * @param menu
     * @return
     */
    @PostMapping("/menu/update")
    public Object updateMenu(@RequestBody Menu menu) {
        if (StringUtils.isEmpty(menu.getMenuId())) {
            throw new SuperBootException("lhq-superboot-menu-0003");
        }
        menuService.updateMenu(menu);
        return RoleMsg.UPDATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 删除菜单
     * 
     * @param menuId
     * @return
     */
    @PostMapping("/menu/delete")
    public Object deleteMenu(@RequestBody Menu menu) {
        if (StringUtils.isEmpty(menu.getMenuId())) {
            throw new SuperBootException("lhq-superboot-menu-0003");
        }
        menuService.deleteMenu(Arrays.asList(menu.getMenuId()));
        return RoleMsg.UPDATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 批量删除菜单
     * 
     * @param menuIdList
     * @return
     */
    @PostMapping("/menu/batchDelete")
    public Object batchDeleteMenu(@RequestBody List<String> menuIdList) {
        if (menuIdList == null || menuIdList.isEmpty()) {
            throw new SuperBootException("lhq-superboot-menu-0003");
        }
        menuService.deleteMenu(menuIdList);
        return RoleMsg.UPDATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 禁用菜单
     * 
     * @param menuId
     * @return
     */
    @PostMapping("/menu/disable")
    public Object disableMenu(@RequestBody Menu menu) {
        if (StringUtils.isEmpty(menu.getMenuId())) {
            throw new SuperBootException("lhq-superboot-menu-0003");
        }
        menuService.disableMenu(Arrays.asList(menu.getMenuId()), IS_ENABLED.NO.value());
        return RoleMsg.UPDATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 批量禁用菜单
     * 
     * @param menuIdList
     * @return
     */
    @PostMapping("/menu/batchDisable")
    public Object batchDisableMenu(@RequestBody List<String> menuIdList) {
        if (menuIdList == null || menuIdList.isEmpty()) {
            throw new SuperBootException("lhq-superboot-menu-0003");
        }
        menuService.disableMenu(menuIdList, IS_ENABLED.NO.value());
        return RoleMsg.UPDATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 查询权限列表
     * 
     * @param roleId
     * @param page
     * @return
     */
    @GetMapping("/menu/roleList")
    public Object roleListRes(String roleId, PageInfo<MenuMsgVo> page) {
        if (StringUtils.isEmpty(roleId)) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        Page<Menu> menuPage = menuService.selectRoleMenu(roleId, page.getPageNum(), page.getPageSize());
        page.setList(menuPage.getResult().stream().map(menu -> MenuMsgVo.convert(menu)).collect(Collectors.toList()));
        page.setTotal(menuPage.getTotal());
        return page;
    }

    /**
     * @Description 授权菜单权限
     * 
     * @param roleMenu
     * @return
     */
    @PostMapping("/menu/authRole")
    public Object authRoleMenu(@RequestBody RoleMenu roleMenu) {
        if (StringUtils.isEmpty(roleMenu.getMenuId())) {
            throw new SuperBootException("lhq-superboot-menu-0003");
        }
        if (StringUtils.isEmpty(roleMenu.getRoleId())) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        menuService.createRoleMenu(roleMenu.getRoleId(), Arrays.asList(roleMenu.getMenuId()));
        return RoleMsg.CREATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 批量授权角色菜单
     * 
     * @param roleMenu
     * @return
     */
    @PostMapping("/menu/batchAuthRole")
    public Object batchAuthRoleMenu(@RequestBody BatchRoleMenuQo roleMenu) {
        if (roleMenu.getMenuIdList() == null || roleMenu.getMenuIdList().isEmpty()) {
            throw new SuperBootException("lhq-superboot-menu-0003");
        }
        if (StringUtils.isEmpty(roleMenu.getRoleId())) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        menuService.createRoleMenu(roleMenu.getRoleId(), roleMenu.getMenuIdList());
        return RoleMsg.CREATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 删除角色菜单
     * 
     * @param roleMenuId
     * @return
     */
    @PostMapping("/menu/delRole")
    public Object delRoleMenu(@RequestBody RoleMenu roleMenu) {
        if (StringUtils.isEmpty(roleMenu.getRoleMenuId())) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        menuService.deleteRoleMenu(Arrays.asList(roleMenu.getRoleMenuId()));
        return RoleMsg.UPDATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 批量删除角色菜单
     * 
     * @param roleMenuIdList
     * @return
     */
    @PostMapping("/menu/batchDelRole")
    public Object batchDelRoleMenu(@RequestBody List<String> roleMenuIdList) {
        if (roleMenuIdList == null || roleMenuIdList.isEmpty()) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        menuService.deleteRoleMenu(roleMenuIdList);
        return RoleMsg.UPDATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 禁用角色菜单
     * 
     * @param roleMenuId
     * @return
     */
    @PostMapping("/menu/disableRole")
    public Object disableRoleMenu(@RequestBody RoleMenu roleMenu) {
        if (StringUtils.isEmpty(roleMenu.getRoleMenuId())) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        menuService.disableRoleMenu(Arrays.asList(roleMenu.getRoleMenuId()), IS_ENABLED.NO.value());
        return RoleMsg.UPDATEMENUSUCCESS.getCode();
    }

    /**
     * @Description 批量禁用角色菜单
     * 
     * @param roleMenuIdList
     * @return
     */
    @PostMapping("/menu/batchDisableRole")
    public Object batchDisableRoleMenu(@RequestBody List<String> roleMenuIdList) {
        if (roleMenuIdList == null || roleMenuIdList.isEmpty()) {
            throw new SuperBootException("lhq-superboot-role-0001");
        }
        menuService.disableRoleMenu(roleMenuIdList, IS_ENABLED.NO.value());
        return RoleMsg.UPDATEMENUSUCCESS.getCode();
    }

}
