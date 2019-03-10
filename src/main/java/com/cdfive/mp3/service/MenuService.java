package com.cdfive.mp3.service;

import java.util.List;

import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.core.vo.ztree.ZtreeNodeVo;
import com.cdfive.mp3.vo.menu.ButtonJqGridRequest;
import com.cdfive.mp3.vo.menu.ButtonListVo;
import com.cdfive.mp3.vo.menu.MenuTreeNodeVo;

public interface MenuService {
	List<ZtreeNodeVo> findTreeList();
	
	ZtreeNodeVo addMenu(String pId, String prevId, String name, String url);
	
	void updateMenu(String id, String name, String url);
	
	void delMenu(String id);
	
	JqGridResponse<ButtonListVo> findButtonJqGridList(ButtonJqGridRequest request);
	
	ZtreeNodeVo addButton(String pId, String name, String url);
	
	void updateButton(String id, String name, String url);
	
	void delButton(String id);
	
	List<MenuTreeNodeVo> findUserMenuList(String userId);
}