package com.lhq.superboot.vo;

import java.util.List;
import java.util.stream.Collectors;

import com.lhq.superboot.domain.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户菜单dto
 *
 * @author: lihaoqi
 * @date: 2019年4月23日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserMenuVo {

	private String menuId;
	private String menuPid;
	private String menuName;
	private int menuSort;
	private String menuUrl;
	private int menuLevel;
	private String menuIcon;

	public static List<UserMenuVo> convert(List<Menu> menuList) {
		return menuList.stream().map(menu -> MenuToDto(menu)).collect(Collectors.toList());
	}

	public static UserMenuVo MenuToDto(Menu menu) {
		UserMenuVo userMenuDto = new UserMenuVo();
		userMenuDto.toBuilder().menuId(menu.getMenuId()).menuPid(menu.getMenuPid()).menuName(menu.getMenuName())
				.menuSort(menu.getMenuSort()).menuUrl(menu.getMenuUrl()).menuLevel(menu.getMenuLevel())
				.menuIcon(menu.getMenuIcon()).build();
		return userMenuDto;

	}

}
