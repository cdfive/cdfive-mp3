package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.constant.Mp3UriConstant;
import com.cdfive.mp3.service.UserService;
import com.cdfive.mp3.vo.session.SessionUserShowVo;
import com.cdfive.mp3.vo.user.UserDetailVo;
import com.cdfive.mp3.vo.user.UserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(Mp3UriConstant.LOGIN)
	@ResponseBody
	public BaseResponse<?> login(String username, String password) {
		userService.login(username, password);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.LOGOUT)
	@ResponseBody
	public BaseResponse<?> logout() {
		userService.logout();
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.USER_SHOW)
	@ResponseBody
	public BaseResponse<SessionUserShowVo> userShow() {
		SessionUserShowVo sessionUserShowVo = userService.userShow();
		return succ(sessionUserShowVo);
	}
	
	@RequestMapping(Mp3UriConstant.USER_LIST)
	@ResponseBody
	public JqGridResponse<UserListVo> list(JqGridRequest request) {
		JqGridResponse<UserListVo> list = userService.findUserJqGridList(request);
		return list;
	}
	
	@RequestMapping(Mp3UriConstant.USER_DETAIL)
	@ResponseBody
	public BaseResponse<UserDetailVo> detail(String id) {
		UserDetailVo userDetailVo = userService.findUserDetail(id);
		return succ(userDetailVo);
	}
	
	@RequestMapping(Mp3UriConstant.USER_ADD)
	@ResponseBody
	public BaseResponse<?> add(String userName, String password, String realName, Integer isAdmin, @RequestParam(required = false, name="roleIds[]") List<String> roleIds) {
		userService.addUser(userName, password, realName, isAdmin, roleIds);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.USER_UPDATE)
	@ResponseBody
	public BaseResponse<?> update(String id, String realName, Integer isAdmin, Integer userStatus, @RequestParam(required = false, name="roleIds[]") List<String> roleIds) {
		userService.updateUser(id, realName, isAdmin, userStatus, roleIds);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.USER_DEL)
	@ResponseBody
	public BaseResponse<?> del(String id) {
		userService.delUser(id);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.USER_CHANGE_PASSWORD)
	@ResponseBody
	public BaseResponse<?> changePasswordd(String oldPassword, String newPassword, String confirmPassword) {
		userService.changePassword(oldPassword, newPassword, confirmPassword);
		return succ();
	}
	
}
