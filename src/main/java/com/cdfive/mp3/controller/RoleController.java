package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.vo.common.IdNameVo;
import com.cdfive.mp3.constant.Mp3UriConstant;
import com.cdfive.mp3.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController extends BaseController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(Mp3UriConstant.ROLE_LIST)
	@ResponseBody
	public BaseResponse<List<IdNameVo>> list() {
		List<IdNameVo> list = roleService.findRoleList();
		return succ(list);
	}
	
	@RequestMapping(Mp3UriConstant.ROLE_ADD)
	@ResponseBody
	public BaseResponse<?> add(String name, String description, @RequestParam(required = false, name = "menuIds[]") List<String> menuIds) {
		roleService.addRole(name, description, menuIds);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.ROLE_UPDATE)
	@ResponseBody
	public BaseResponse<?> update(String id, String name, String description, @RequestParam(required = false, name = "menuIds[]") List<String> menuIds) {
		roleService.updateRole(id, name, description, menuIds); 
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.ROLE_DEL)
	@ResponseBody
	public BaseResponse<?> del(String id) {
		roleService.delRole(id);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.ROLE_MENU_LIST)
	@ResponseBody
	public BaseResponse<List<String>> menuList(String id) {
		List<String> list = roleService.findMenuIdListByRoleId(id);
		return succ(list);
	}
	
}
