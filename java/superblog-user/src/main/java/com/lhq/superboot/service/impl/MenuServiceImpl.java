package com.lhq.superboot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lhq.superboot.domain.Menu;
import com.lhq.superboot.domain.MenuExample;
import com.lhq.superboot.domain.RoleMenu;
import com.lhq.superboot.domain.RoleMenuExample;
import com.lhq.superboot.enums.ConstEnumUtils.IS_DELETE;
import com.lhq.superboot.enums.ConstEnumUtils.IS_ENABLED;
import com.lhq.superboot.exception.SuperBootException;
import com.lhq.superboot.mapper.MenuMapper;
import com.lhq.superboot.mapper.RoleMenuMapper;
import com.lhq.superboot.service.MenuService;
import com.lhq.superboot.service.UserService;
import com.lhq.superboot.util.StringUtils;

/**
 * @Description: 菜单实现
 *
 * @author: lihaoqi
 * @date: 2019年5月6日
 * @version: 1.0.0
 */
@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private UserService userService;

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Override
	public Page<Menu> selectMenuByPage(String menuName, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		MenuExample menuExample = new MenuExample();
		MenuExample.Criteria criteria = menuExample.createCriteria();
		if (null != menuName) {
			criteria.andMenuNameLike("%" + menuName + "%");
		}
		criteria.andIsEnabledEqualTo(IS_ENABLED.YES.value()).andIsDeletedEqualTo(IS_DELETE.NO.value());
		Page<Menu> menuPage = (Page<Menu>) menuMapper.selectByExample(menuExample);

		return menuPage;
	}

	@Override
	public void createMenu(Menu menu) {
		String userId = userService.getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			throw new SuperBootException("lhq-superboot-user-0021");
		}
		Date date = new Date();

		Menu menuParam = new Menu().toBuilder().channelId(menu.getChannelId()).menuName(menu.getMenuName())
				.menuSort(menu.getMenuSort()).menuIcon(menu.getMenuIcon()).menuPid(menu.getMenuPid())
				.menuUrl(menu.getMenuUrl()).menuLevel(menu.getMenuLevel()).createTime(date).createUser(userId)
				.modifyTime(date).modifyUser(userId).isDeleted(IS_DELETE.NO.value()).isEnabled(IS_ENABLED.YES.value())
				.build();
		menuMapper.insertSelective(menuParam);
	}

	@Override
	public void updateMenu(Menu menu) {
		String userId = userService.getCurrentUserId();
		if (StringUtils.isEmpty(userId)) {
			throw new SuperBootException("lhq-superboot-user-0021");
		}

		Menu menuParam = new Menu().toBuilder().menuId(menu.getMenuId()).modifyTime(new Date()).modifyUser(userId)
				.build();
		if (StringUtils.isNotEmpty(menu.getChannelId())) {
			menuParam.setChannelId(menu.getChannelId());
		}
		if (StringUtils.isNotEmpty(menu.getMenuName())) {
			menuParam.setMenuName(menu.getMenuName());
		}
		if (StringUtils.isNotEmpty(menu.getMenuIcon())) {
			menuParam.setMenuIcon(menu.getMenuIcon());
		}
		if (StringUtils.isNotEmpty(menu.getMenuPid())) {
			menuParam.setMenuPid(menu.getMenuPid());
		}
		if (StringUtils.isNotEmpty(menu.getMenuUrl())) {
			menuParam.setMenuUrl(menu.getMenuUrl());
		}
		if (null != menu.getMenuLevel()) {
			menuParam.setMenuLevel(menu.getMenuLevel());
		}
		if (null != menu.getMenuSort()) {
			menuParam.setMenuSort(menu.getMenuSort());
		}
		menuMapper.updateByPrimaryKeySelective(menuParam);
	}

	@Override
	public void disableMenu(List<String> menuIdList, Integer isEnabled) {
		MenuExample menuExample = new MenuExample();
		menuExample.createCriteria().andMenuIdIn(menuIdList);
		Menu menu = new Menu().toBuilder().isEnabled(isEnabled).build();
		menuMapper.updateByExampleSelective(menu, menuExample);
	}

	@Override
	public void deleteMenu(List<String> menuIdList) {
		MenuExample menuExample = new MenuExample();
		menuExample.createCriteria().andMenuIdIn(menuIdList);
		Menu menu = new Menu().toBuilder().isDeleted(IS_DELETE.YES.value()).build();
		menuMapper.updateByExampleSelective(menu, menuExample);
	}

	@Override
	public Page<Menu> selectRoleMenu(String roleId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		RoleMenuExample roleMenuExample = new RoleMenuExample();
		roleMenuExample.createCriteria().andRoleIdEqualTo(roleId).andIsEnabledEqualTo(IS_ENABLED.YES.value());
		List<RoleMenu> roleMenuList = roleMenuMapper.selectByExample(roleMenuExample);

		if (roleMenuList.isEmpty()) {
			return null;
		}

		MenuExample menuExample = new MenuExample();
		menuExample.createCriteria()
				.andMenuIdIn(roleMenuList.stream().map(roleMenu -> roleMenu.getMenuId()).collect(Collectors.toList()))
				.andIsDeletedEqualTo(IS_DELETE.NO.value()).andIsEnabledEqualTo(IS_ENABLED.YES.value());
		Page<Menu> menuPage = (Page<Menu>) menuMapper.selectByExample(menuExample);
		return menuPage;
	}

	@Override
	@Transactional
	public void createRoleMenu(String roleId, List<String> menuIdList) {
		Date date = new Date();

		for (String menuId : menuIdList) {
			RoleMenu roleMenuParam = new RoleMenu().toBuilder().menuId(menuId).roleId(roleId).createTime(date)
					.modifyTime(date).isEnabled(IS_ENABLED.YES.value()).build();
			roleMenuMapper.insertSelective(roleMenuParam);
		}
	}

	@Override
	public void disableRoleMenu(List<String> roleMenuIdList, Integer isEnabled) {
		RoleMenuExample roleMenuExample = new RoleMenuExample();
		roleMenuExample.createCriteria().andRoleMenuIdIn(roleMenuIdList);
		RoleMenu roleMenu = new RoleMenu().toBuilder().isEnabled(isEnabled).build();
		roleMenuMapper.updateByExampleSelective(roleMenu, roleMenuExample);
	}

	@Override
	public void deleteRoleMenu(List<String> roleMenuIdList) {
		RoleMenuExample roleMenuExample = new RoleMenuExample();
		roleMenuExample.createCriteria().andRoleMenuIdIn(roleMenuIdList);
		roleMenuMapper.deleteByExample(roleMenuExample);
	}

}
