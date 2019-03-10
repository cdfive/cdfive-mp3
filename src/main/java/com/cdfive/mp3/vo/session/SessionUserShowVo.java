package com.cdfive.mp3.vo.session;

import com.cdfive.mp3.vo.menu.MenuTreeNodeVo;

import java.util.List;

public class SessionUserShowVo {
	private String realName;
	private List<MenuTreeNodeVo> menus;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<MenuTreeNodeVo> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuTreeNodeVo> menus) {
		this.menus = menus;
	}

}
