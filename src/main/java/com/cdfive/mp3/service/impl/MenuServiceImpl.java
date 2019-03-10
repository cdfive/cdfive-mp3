package com.cdfive.mp3.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.constant.enums.YesNoEnum;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.core.vo.ztree.ZtreeNodeVo;
import com.cdfive.mp3.constant.enums.db.Menu_typeEnum;
import com.cdfive.mp3.mapper.MenuMapper;
import com.cdfive.mp3.mapper.MenuMapperCustom;
import com.cdfive.mp3.mapper.UserMapper;
import com.cdfive.mp3.po.Menu;
import com.cdfive.mp3.po.User;
import com.cdfive.mp3.service.MenuService;
import com.cdfive.mp3.service.Mp3BaseService;
import com.cdfive.mp3.vo.menu.ButtonJqGridRequest;
import com.cdfive.mp3.vo.menu.ButtonListVo;
import com.cdfive.mp3.vo.menu.MenuTreeNodeVo;

@Service
public class MenuServiceImpl extends Mp3BaseService implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private MenuMapperCustom menuMapperCustom;
	@Autowired
	private UserMapper userMapper; 
	
	@Override
	public List<ZtreeNodeVo> findTreeList() {
		addBizLog("查询菜单树");
		List<ZtreeNodeVo> list = menuMapperCustom.findMenuTreeList();
		return list;
	}

	@Override
	public ZtreeNodeVo addMenu(String pId, String prevId, String name, String url) {
		addBizLog("新增菜单");
		checkNotEmpty(name, "名称");
		String menuId = uuid();
		Menu menu = new Menu();
		menu.setId(menuId);
		menu.setType(Menu_typeEnum.MENU.getCode());
		menu.setName(name);
		menu.setUrl(url);
		menu.setCreateUserId(getSessionUserId());
		menu.setUpdateUserId(getSessionUserId());
		menu.setCreateTime(now());
		menu.setUpdateTime(now());
		menu.setStatus(StatusEnum.NORMAL.getCode());
//		if (pId == null) {
//			if (prevId == null) {
//				Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(null, Menu_typeEnum.MENU.getCode(), name);
//				if (tmpMenu != null) {
//					fail("名称不能重复");
//				}
//				Integer maxSort = menuMapperCustom.findMaxSortByPIdAndType(null, Menu_typeEnum.MENU.getCode());
//				if (maxSort == null || maxSort == 0) {
//					maxSort = 1;
//				} else {
//					maxSort++;
//				}
//				menu.setpId(null);
//				menu.setSort(maxSort);
//			} else {
//				Menu prevMenu = checkMenu(prevId);
//				String prevPId = prevMenu.getpId();
//				Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(prevPId, Menu_typeEnum.MENU.getCode(), name);
//				if (tmpMenu != null) {
//					fail("名称不能重复");
//				}
//				Integer prevSort = prevMenu.getSort();
//				menuMapperCustom.addAfterSort(prevPId, Menu_typeEnum.MENU.getCode(), prevSort);
//				menu.setpId(prevPId);
//				menu.setSort(prevSort);
//			}
//		} else {
//			checkMenu(pId);
//			Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(pId, Menu_typeEnum.MENU.getCode(), name);
//			if (tmpMenu != null) {
//				fail("名称不能重复");
//			}
//			Integer maxSort = menuMapperCustom.findMaxSortByPIdAndType(pId, Menu_typeEnum.MENU.getCode());
//			if (maxSort == null || maxSort == 0) {
//				maxSort = 1;
//			} else {
//				maxSort++;
//			}
//			menu.setpId(pId);
//			menu.setSort(maxSort);
//		}
		
//		if (prevId == null) {
//			if (pId != null) {
//				checkMenu(pId);
//			}
//			Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(pId, Menu_typeEnum.MENU.getCode(), name);
//			if (tmpMenu != null) {
//				fail("名称不能重复");
//			}
//			menuMapperCustom.addAfterSort(pId, Menu_typeEnum.MENU.getCode(), 0);
//			menu.setpId(pId);
//			menu.setSort(1);
//		} else {
//			Menu prevMenu = checkMenu(prevId);
//			String prevPId = prevMenu.getpId();
//			Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(prevPId, Menu_typeEnum.MENU.getCode(), name);
//			if (tmpMenu != null) {
//				fail("名称不能重复");
//			}
//			Integer prevSort = prevMenu.getSort();
//			menuMapperCustom.addAfterSort(prevPId, Menu_typeEnum.MENU.getCode(), prevSort);
//			menu.setpId(prevPId);
//			menu.setSort(prevSort + 1);
//		}
		
		if (pId != null || (pId == null && prevId == null)) {
			if (pId != null) {
				checkMenu(pId);
			}
			Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(pId, Menu_typeEnum.MENU.getCode(), name);
			if (tmpMenu != null) {
				fail("名称不能重复");
			}
			menuMapperCustom.addAfterSort(pId, Menu_typeEnum.MENU.getCode(), 0);
			menu.setpId(pId);
			menu.setSort(1);
		} else {
			Menu prevMenu = checkMenu(prevId);
			String prevPId = prevMenu.getpId();
			Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(prevPId, Menu_typeEnum.MENU.getCode(), name);
			if (tmpMenu != null) {
				fail("名称不能重复");
			}
			Integer prevSort = prevMenu.getSort();
			menuMapperCustom.addAfterSort(prevPId, Menu_typeEnum.MENU.getCode(), prevSort);
			menu.setpId(prevPId);
			menu.setSort(prevSort + 1);
		}
		
		menuMapper.insertSelective(menu);
		ZtreeNodeVo ztreeNodeVo = new ZtreeNodeVo();
		ztreeNodeVo.setId(menuId);
		ztreeNodeVo.setName(name);
		ztreeNodeVo.setUrl(url);
		ztreeNodeVo.setpId(pId);
		ztreeNodeVo.setSort(menu.getSort());
		return ztreeNodeVo;
	}

	@Override
	public void updateMenu(String id, String name, String url) {
		addBizLog("修改菜单");
		checkNotEmpty(id, "编号");
		checkNotEmpty(name, "名称");
		Menu menu = checkMenu(id);
		if (!menu.getName().equals(name)) {
			Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(menu.getpId(), Menu_typeEnum.MENU.getCode(), name);
			if (tmpMenu != null) {
				fail("名称不能重复");
			}
		}
		menu.setName(name);
		menu.setUrl(url);
		menu.setUpdateUserId(getSessionUserId());
		menu.setUpdateTime(now());
		menuMapper.updateByPrimaryKey(menu);
	}

	@Override
	public void delMenu(String id) {
		addBizLog("删除菜单");
		checkNotEmpty(id, "编号");
		Menu menu = checkMenu(id);
		int subMenuCnt = menuMapperCustom.findCountByPIdAndType(id, Menu_typeEnum.MENU.getCode());
		if (subMenuCnt > 0) {
			fail("菜单下有子菜单");
		}
		int subButtonCnt = menuMapperCustom.findCountByPIdAndType(id, Menu_typeEnum.BUTTON.getCode());
		if (subButtonCnt > 0) {
			fail("菜单下有按钮");
		}
		menu.setStatus(StatusEnum.DELETED.getCode());
		menu.setUpdateUserId(getSessionUserId());
		menu.setUpdateTime(now());
		menuMapper.updateByPrimaryKeySelective(menu);
		menuMapperCustom.minusAfterSort(menu.getpId(), Menu_typeEnum.MENU.getCode(), menu.getSort());
	}
	
	@Override
	public JqGridResponse<ButtonListVo> findButtonJqGridList(ButtonJqGridRequest request) {
		addBizLog("查询按钮列表");
		String pId = request.getpId();
		checkNotEmpty(pId, "父编号");
		int records = menuMapperCustom.findButtonJqGridListCount(request);
		List<ButtonListVo> rows = menuMapperCustom.findButtonJqGridList(request);
		JqGridResponse<ButtonListVo> jqGridList = new JqGridResponse<ButtonListVo>(request, records, rows);
		return jqGridList;
	}
	
	@Override
	public ZtreeNodeVo addButton(String pId, String name, String url) {
		addBizLog("新增按钮");
		checkNotEmpty(pId, "父编号");
		checkNotEmpty(name, "名称");
		checkNotEmpty(url, "地址");
		checkMenu(pId);
		Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(pId, Menu_typeEnum.BUTTON.getCode(), name);
		if (tmpMenu != null) {
			fail("名称不能重复");
		}
		
		Integer maxSort = menuMapperCustom.findMaxSortByPIdAndType(pId, Menu_typeEnum.BUTTON.getCode());
		if (maxSort == null || maxSort == 0) {
			maxSort = 1;
		} else {
			maxSort++;
		}
		String menuId = uuid();
		Menu menu = new Menu();
		menu.setId(menuId);
		menu.setType(Menu_typeEnum.BUTTON.getCode());
		menu.setName(name);
		menu.setUrl(url);
		menu.setpId(pId);
		menu.setSort(maxSort);
		menu.setCreateUserId(getSessionUserId());
		menu.setUpdateUserId(getSessionUserId());
		menu.setCreateTime(now());
		menu.setUpdateTime(now());
		menu.setStatus(StatusEnum.NORMAL.getCode());
		menuMapper.insertSelective(menu);
		ZtreeNodeVo ztreeNodeVo = new ZtreeNodeVo();
		ztreeNodeVo.setId(menuId);
		ztreeNodeVo.setName(name);
		ztreeNodeVo.setUrl(url);
		ztreeNodeVo.setpId(pId);
		ztreeNodeVo.setSort(maxSort);
		return ztreeNodeVo;
	}

	@Override
	public void updateButton(String id, String name, String url) {
		addBizLog("修改按钮");
		checkNotEmpty(id, "编号");
		checkNotEmpty(name, "名称");
		checkNotEmpty(url, "地址");
		Menu menu = checkButton(id);
		if (!menu.getName().equals(name)) {
			Menu tmpMenu = menuMapperCustom.findByPIdAndTypeAndName(menu.getpId(), Menu_typeEnum.BUTTON.getCode(), name);
			if (tmpMenu != null) {
				fail("名称不能重复");
			}
		}
		menu.setName(name);
		menu.setUrl(url);
		menu.setUpdateUserId(getSessionUserId());
		menu.setUpdateTime(now());
		menuMapper.updateByPrimaryKey(menu);
	}

	@Override
	public void delButton(String id) {
		addBizLog("删除按钮");
		checkNotEmpty(id, "编号");
		Menu menu = checkButton(id);
		menu.setStatus(StatusEnum.DELETED.getCode());
		menu.setUpdateUserId(getSessionUserId());
		menu.setUpdateTime(now());
		menuMapper.updateByPrimaryKeySelective(menu);
		menuMapperCustom.minusAfterSort(menu.getpId(), Menu_typeEnum.BUTTON.getCode(), menu.getSort());
	}

	
	private Menu checkMenu(String id) {
		Menu menu = menuMapper.selectByPrimaryKey(id);
		if (menu == null || StatusEnum.DELETED.getCode().equals(menu.getStatus())) {
			fail("菜单不存在或已删除");
		}
		if (!Menu_typeEnum.MENU.getCode().equals(menu.getType())) {
			fail("不是菜单");
		}
		return menu;
	}
	
	private Menu checkButton(String id) {
		Menu menu = menuMapper.selectByPrimaryKey(id);
		if (menu == null || StatusEnum.DELETED.getCode().equals(menu.getStatus())) {
			fail("按钮不存在或已删除");
		}
		if (!Menu_typeEnum.BUTTON.getCode().equals(menu.getType())) {
			fail("不是按钮");
		}
		return menu;
	}

	@Override
	public List<MenuTreeNodeVo> findUserMenuList(String userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		List<MenuTreeNodeVo> rootMenuList;
		if (YesNoEnum.YES.getCode().equals(user.getIsAdmin())) {
			rootMenuList = menuMapperCustom.findMenuTreeNodeList(null, Menu_typeEnum.MENU.getCode());
			findUserMenuListRecursion(rootMenuList);
		} else {
			rootMenuList = menuMapperCustom.findUserMenuTreeNodeList(userId, null, Menu_typeEnum.MENU.getCode());
			findUserMenuListRecursion(rootMenuList, userId);
		}
		return rootMenuList;
	}
	
	private void findUserMenuListRecursion(List<MenuTreeNodeVo> menuList) {
		for (MenuTreeNodeVo menuTreeNodeVo : menuList) {
			List<MenuTreeNodeVo> subMenuList = menuMapperCustom.findMenuTreeNodeList(menuTreeNodeVo.getId(), Menu_typeEnum.MENU.getCode());
			if (isEmpty(subMenuList)) {
				continue;
			} else {
				menuTreeNodeVo.setSubMenu(subMenuList);
				findUserMenuListRecursion(subMenuList);
			}
		}
	}
	
	private void findUserMenuListRecursion(List<MenuTreeNodeVo> menuList, String userId) {
		for (MenuTreeNodeVo menuTreeNodeVo : menuList) {
			List<MenuTreeNodeVo> subMenuList = menuMapperCustom.findUserMenuTreeNodeList(userId, menuTreeNodeVo.getId(), Menu_typeEnum.MENU.getCode());
			if (isEmpty(subMenuList)) {
				continue;
			} else {
				menuTreeNodeVo.setSubMenu(subMenuList);
				findUserMenuListRecursion(subMenuList, userId);
			}
		}
	}
}
