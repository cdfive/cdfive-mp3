package com.cdfive.mp3.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cdfive.core.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cdfive.core.base.BaseService;
import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.constant.enums.YesNoEnum;
import com.cdfive.core.util.DateUtil;
import com.cdfive.core.vo.ztree.ZtreeNodeVo;
import com.cdfive.mp3.constant.Mp3Constant;
import com.cdfive.mp3.constant.enums.db.User_userStatusEnum;
import com.cdfive.mp3.mapper.MenuMapperCustom;
import com.cdfive.mp3.mapper.RoleMapperCustom;
import com.cdfive.mp3.mapper.RoleMenuMapperCustom;
import com.cdfive.mp3.mapper.UserMapper;
import com.cdfive.mp3.mapper.UserMapperCustom;
import com.cdfive.mp3.mapper.UserRoleMapperCustom;
import com.cdfive.mp3.po.User;
import com.cdfive.mp3.service.InitService;
import com.cdfive.mp3.service.MenuService;
import com.cdfive.mp3.service.RoleService;
import com.cdfive.mp3.service.UserService;
import com.cdfive.mp3.vo.session.SessionUserVo;

@Service
public class InitServiceImpl extends BaseService implements InitService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserMapperCustom userMapperCustom;
	@Autowired
	private UserRoleMapperCustom userRoleMapperCustom;
	@Autowired
	private RoleMenuMapperCustom roleMenuMapperCustom;
	@Autowired
	private MenuMapperCustom menuMapperCustom; 
	@Autowired
	private RoleMapperCustom roleMapperCustom;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	@Override
	public void init() {
		String userFiveId = "b3749b1c-175a-11e7-be4d-003067e0a3f2";
		String userAdminId = "c1cfd5aa-32d4-11e7-8417-003067e0a3f2";
		String userMp3Id = "c8ef6ac8-32d4-11e7-8417-003067e0a3f2";
//		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletRequest request = WebUtil.getCurrentHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		HttpSession httpSession = request.getSession();
		SessionUserVo sessionUserVo = new SessionUserVo();
		sessionUserVo.setUserId(userFiveId);
		httpSession.setAttribute(Mp3Constant.MP3_SESSION_USER, sessionUserVo);

		userRoleMapperCustom.truncate();
		roleMenuMapperCustom.truncate();
		menuMapperCustom.truncate();
		roleMapperCustom.truncate();
		userMapperCustom.truncate();

		User userFive = new User();
		userFive.setId(userFiveId);
		userFive.setUserName("five");
		userFive.setPassword("6999f8fc402ea4811c046770c9e2e102");
		userFive.setRealName("超级管理员");
		userFive.setErrCount(0);
		userFive.setIsAdmin(YesNoEnum.YES.getCode());
		userFive.setUserStatus(User_userStatusEnum.NORMAL.getCode());
		userFive.setCreateTime(DateUtil.now());
		userFive.setUpdateTime(DateUtil.now());
		userFive.setStatus(StatusEnum.NORMAL.getCode());
		userMapper.insertSelective(userFive);

		User userAdmin = new User();
		userAdmin.setId(userAdminId);
		userAdmin.setUserName("admin");
		userAdmin.setPassword("c80e999bdba0e8956428491050529392");
		userAdmin.setRealName("管理员");
		userAdmin.setErrCount(0);
		userAdmin.setIsAdmin(YesNoEnum.NO.getCode());
		userAdmin.setUserStatus(User_userStatusEnum.NORMAL.getCode());
		userAdmin.setCreateTime(DateUtil.now());
		userAdmin.setUpdateTime(DateUtil.now());
		userAdmin.setStatus(StatusEnum.NORMAL.getCode());
		userMapper.insertSelective(userAdmin);

		User userMp3 = new User();
		userMp3.setId(userMp3Id);
		userMp3.setUserName("mp3");
		userMp3.setPassword("7cfaf444f6d5277a0390449b9f03d8bd");
		userMp3.setRealName("mp3管理员");
		userMp3.setErrCount(0);
		userMp3.setIsAdmin(YesNoEnum.NO.getCode());
		userMp3.setUserStatus(User_userStatusEnum.NORMAL.getCode());
		userMp3.setCreateTime(DateUtil.now());
		userMp3.setUpdateTime(DateUtil.now());
		userMp3.setStatus(StatusEnum.NORMAL.getCode());
		userMapper.insertSelective(userMp3);

		List<String> allMenuIds = new ArrayList<String>();
		List<String> mp3MenuIds = new ArrayList<String>();
		ZtreeNodeVo ztreeNodeVo;
		String pId;

		ZtreeNodeVo menuIndex = menuService.addMenu(null, null, "首页", "/admin/dashboard");
		allMenuIds.add(menuIndex.getId());
		
		ZtreeNodeVo menuMp3 = menuService.addMenu(null, menuIndex.getId(), "mp3管理", null);
		allMenuIds.add(menuMp3.getId());
		mp3MenuIds.add(menuMp3.getId());
		pId = menuMp3.getId();
		ztreeNodeVo = menuService.addMenu(pId, null, "mp3管理", "/admin/mp3/mp3_list");
		allMenuIds.add(ztreeNodeVo.getId());
		mp3MenuIds.add(ztreeNodeVo.getId());
		
		ZtreeNodeVo menuCms = menuService.addMenu(null, menuMp3.getId(), "内容管理", null);
		allMenuIds.add(menuCms.getId());		
		pId = menuCms.getId();
		ztreeNodeVo = menuService.addMenu(pId, null, "栏目管理", "/admin/cms/catalog/catalog_list");
		allMenuIds.add(ztreeNodeVo.getId());	
		ztreeNodeVo = menuService.addMenu(null, ztreeNodeVo.getId(), "文章管理", "/admin/cms/article/article_list");
		allMenuIds.add(ztreeNodeVo.getId());

		ZtreeNodeVo menuSystem = menuService.addMenu(null, menuCms.getId(), "系统管理", null);
		allMenuIds.add(menuSystem.getId());
		pId = menuSystem.getId();
		ztreeNodeVo = menuService.addMenu(pId, null, "用户管理", "/admin/system/user/user_list");
		allMenuIds.add(ztreeNodeVo.getId());
		ztreeNodeVo = menuService.addMenu(null, ztreeNodeVo.getId(), "角色管理", "/admin/system/role/role_list");
		allMenuIds.add(ztreeNodeVo.getId());
		ztreeNodeVo = menuService.addMenu(null, ztreeNodeVo.getId(), "菜单管理", "/admin/system/menu/menu_list");
		allMenuIds.add(ztreeNodeVo.getId());

		ZtreeNodeVo menuLog = menuService.addMenu(null, menuSystem.getId(), "日志管理", null);
		allMenuIds.add(menuLog.getId());
		pId = menuLog.getId();
		ztreeNodeVo = menuService.addMenu(pId, null, "登录日志", "/admin/log/login_log_list");
		allMenuIds.add(ztreeNodeVo.getId());
		ztreeNodeVo = menuService.addMenu(null, ztreeNodeVo.getId(), "业务日志", "/admin/log/biz_log_list");
		allMenuIds.add(ztreeNodeVo.getId());

		String roleId = roleService.addRole("管理员", null, allMenuIds);
		userService.updateUserRole(userAdmin.getId(), Arrays.asList(roleId));

		roleId = roleService.addRole("mp3管理员", null, mp3MenuIds);
		userService.updateUserRole(userMp3.getId(), Arrays.asList(roleId));
	}

}
