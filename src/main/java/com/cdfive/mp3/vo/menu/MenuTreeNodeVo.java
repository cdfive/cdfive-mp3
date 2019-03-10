package com.cdfive.mp3.vo.menu;

import java.util.List;

public class MenuTreeNodeVo {
	private String id;
	private String name;
	private String url;
	private List<MenuTreeNodeVo> subMenu;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuTreeNodeVo> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<MenuTreeNodeVo> subMenu) {
		this.subMenu = subMenu;
	}

}
