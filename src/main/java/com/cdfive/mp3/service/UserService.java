package com.cdfive.mp3.service;

import java.util.List;

import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.po.User;
import com.cdfive.mp3.vo.session.SessionUserShowVo;
import com.cdfive.mp3.vo.user.UserDetailVo;
import com.cdfive.mp3.vo.user.UserListVo;

public interface UserService {
	User find(String id);
	
	List<User> findList();
	
	void login(String username, String password);
	
	void logout();
	
	SessionUserShowVo userShow();
	
	JqGridResponse<UserListVo> findUserJqGridList(JqGridRequest request);
	
	UserDetailVo findUserDetail(String id);
	
	void addUser(String userName, String password, String realName, Integer isAdmin, List<String> roleIds);
	
	void updateUser(String id, String realName, Integer isAdmin, Integer userStatus, List<String> roleIds);
	
	void updateUserRole(String id, List<String> roleIds);
	
	void delUser(String id);
	
	void changePassword(String oldPassword, String newPassword, String confirmPassword);
}
