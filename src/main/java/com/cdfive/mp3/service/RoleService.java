package com.cdfive.mp3.service;

import java.util.List;

import com.cdfive.core.vo.common.IdNameVo;

public interface RoleService {
	List<IdNameVo> findRoleList();
	
	String addRole(String name, String description, List<String> menuIds);
	
	void updateRole(String id, String name, String description, List<String> menuIds);
	
	void delRole(String id);
	
	List<String> findMenuIdListByRoleId(String id);
	
}
