package com.lhq.superboot.vo;

import com.lhq.superboot.domain.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 菜单信息dto
 *
 * @author: lihaoqi
 * @date: 2019年5月6日
 * @version: 1.0.0
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MenuMsgVo {

	private String menuId;
	private String menuName;
	private String menuPid;
	private Integer menuSort;
	private String menuUrl;
	private Integer menuLevel;
	private String menuIcon;

	public static MenuMsgVo convert(Menu menu) {
		MenuMsgVo menuMsg = new MenuMsgVo().toBuilder().menuId(menu.getMenuId()).menuName(menu.getMenuName())
				.menuPid(menu.getMenuPid()).menuSort(menu.getMenuSort()).menuUrl(menu.getMenuUrl())
				.menuLevel(menu.getMenuLevel()).menuIcon(menu.getMenuIcon()).build();
		return menuMsg;
	}
}
