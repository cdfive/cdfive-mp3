package com.cdfive.mp3.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.constant.enums.YesNoEnum;
import com.cdfive.core.vo.common.IdNameVo;
import com.cdfive.mp3.mapper.RoleMapper;
import com.cdfive.mp3.mapper.RoleMapperCustom;
import com.cdfive.mp3.mapper.RoleMenuMapperCustom;
import com.cdfive.mp3.po.Role;
import com.cdfive.mp3.po.RoleMenu;
import com.cdfive.mp3.service.Mp3BaseService;
import com.cdfive.mp3.service.RoleService;

@Service
public class RoleServiceImpl extends Mp3BaseService implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleMapperCustom roleMapperCustom;
	@Autowired
	private RoleMenuMapperCustom roleMenuMapperCustom;
	
	@Override
	public List<IdNameVo> findRoleList() {
		addBizLog("查询角色列表");
		List<IdNameVo> list = roleMapperCustom.findRoleList();
		return list;
	}

	@Override
	public String addRole(String name, String description, List<String> menuIds) {
		addBizLog("新增角色");
		checkNotEmpty(name, "名称");
		int count = roleMapperCustom.findCountByName(name);
		if (count > 0) {
			fail("名称不能重复");
		}
		Role role = new Role();
		role.setId(uuid());
		role.setName(name);
		role.setDescription(description);
		role.setIsEnable(YesNoEnum.YES.getCode());
		role.setCreateUserId(getSessionUserId());
		role.setUpdateUserId(getSessionUserId());
		role.setCreateTime(now());
		role.setUpdateTime(now());
		role.setStatus(StatusEnum.NORMAL.getCode());
		roleMapper.insertSelective(role);
		if (isNotEmpty(menuIds)) {
			List<RoleMenu> roleMenus = new ArrayList<RoleMenu>();
			for (String menuId : menuIds) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setId(uuid());
				roleMenu.setRoleId(role.getId());
				roleMenu.setMenuId(menuId);
				roleMenu.setCreateUserId(getSessionUserId());
				roleMenu.setUpdateUserId(getSessionUserId());
				roleMenu.setCreateTime(now());
				roleMenu.setUpdateTime(now());
				roleMenu.setStatus(StatusEnum.NORMAL.getCode());
				roleMenus.add(roleMenu);
			}
			roleMenuMapperCustom.batchInsert(roleMenus);
		}
		return role.getId();
	}

	@Override
	public void updateRole(String id, String name, String description, List<String> menus) {
		addBizLog("修改角色");
		checkNotEmpty(id, "编号");
		checkNotEmpty(name, "名称");
		Role role = roleMapper.selectByPrimaryKey(id);
		if (role == null) {
			fail("角色不存在");
		}
		if (StatusEnum.DELETED.getCode().equals(role.getStatus())) {
			fail("角色已删除");
		}
		role.setName(name);
		role.setDescription(description);
		role.setUpdateUserId(getSessionUserId());
		role.setUpdateTime(now());
		roleMapper.updateByPrimaryKey(role);
		roleMenuMapperCustom.deleteByRoleId(id);
		if (isNotEmpty(menus)) {
			List<RoleMenu> roleMenus = new ArrayList<RoleMenu>();
			for (String menuId : menus) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setId(uuid());
				roleMenu.setRoleId(role.getId());
				roleMenu.setMenuId(menuId);
				roleMenu.setCreateUserId(getSessionUserId());
				roleMenu.setUpdateUserId(getSessionUserId());
				roleMenu.setCreateTime(now());
				roleMenu.setUpdateTime(now());
				roleMenu.setStatus(StatusEnum.NORMAL.getCode());
				roleMenus.add(roleMenu);
			}
			roleMenuMapperCustom.batchInsert(roleMenus);
		}
	}

	@Override
	public void delRole(String id) {
		addBizLog("删除角色");
		checkNotEmpty(id, "编号");
		Role role = roleMapper.selectByPrimaryKey(id);
		if (role == null) {
			fail("角色不存在");
		}
		if (StatusEnum.DELETED.getCode().equals(role.getStatus())) {
			fail("角色已删除");
		}
		roleMenuMapperCustom.deleteByRoleId(role.getId());
		role.setStatus(StatusEnum.DELETED.getCode());
		role.setUpdateUserId(getSessionUserId());
		role.setUpdateTime(now());
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public List<String> findMenuIdListByRoleId(String id) {
		addBizLog("查询角色的菜单编号列表");
		checkNotEmpty(id, "编号");
		Role role = roleMapper.selectByPrimaryKey(id);
		if (role == null) {
			fail("角色不存在");
		}
		if (StatusEnum.DELETED.getCode().equals(role.getStatus())) {
			fail("角色已删除");
		}
		List<String> list = roleMenuMapperCustom.findMenuIdListByRoleId(id);
		return list;
	}

}
