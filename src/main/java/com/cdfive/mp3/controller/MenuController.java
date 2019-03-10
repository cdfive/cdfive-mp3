package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.core.vo.ztree.ZtreeNodeVo;
import com.cdfive.mp3.constant.Mp3UriConstant;
import com.cdfive.mp3.service.MenuService;
import com.cdfive.mp3.vo.menu.ButtonJqGridRequest;
import com.cdfive.mp3.vo.menu.ButtonListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MenuController extends BaseController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(Mp3UriConstant.MENU_LIST)
	@ResponseBody
	public List<ZtreeNodeVo> menuList() {
		return menuService.findTreeList();
	}
	
	@RequestMapping(Mp3UriConstant.MENU_ADD)
	@ResponseBody
	public BaseResponse<ZtreeNodeVo> menuAdd(String pId, String prevId, String name, String url) {
		ZtreeNodeVo ztreeNodeVo = menuService.addMenu(pId, prevId, name, url);
		return succ(ztreeNodeVo);
	}
	
	@RequestMapping(Mp3UriConstant.MENU_UPDATE)
	@ResponseBody
	public BaseResponse<?> menuUpdate(String id, String name, String url) {
		menuService.updateMenu(id, name, url);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.MENU_DEL)
	@ResponseBody
	public BaseResponse<?> menuDel(String id) {
		menuService.delMenu(id);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.BUTTON_LIST)
	@ResponseBody
	public JqGridResponse<ButtonListVo> buttonList(ButtonJqGridRequest request) {
		return menuService.findButtonJqGridList(request);
	}
	
	@RequestMapping(Mp3UriConstant.BUTTON_ADD)
	@ResponseBody
	public BaseResponse<ZtreeNodeVo> buttonAdd(String pId, String name, String url) {
		ZtreeNodeVo ztreeNodeVo = menuService.addButton(pId, name, url);
		return succ(ztreeNodeVo);
	}
	
	@RequestMapping(Mp3UriConstant.BUTTON_UPDATE)
	@ResponseBody
	public BaseResponse<?> buttonUpdate(String id, String name, String url) {
		menuService.updateButton(id, name, url);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.BUTTON_DEL)
	@ResponseBody
	public BaseResponse<?> buttonDel(String id) {
		menuService.delButton(id);
		return succ();
	}
	
}
