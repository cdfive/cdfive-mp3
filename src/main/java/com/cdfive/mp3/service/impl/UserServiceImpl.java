package com.cdfive.mp3.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.constant.enums.YesNoEnum;
import com.cdfive.core.util.Md5Util;
import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.constant.Mp3Constant;
import com.cdfive.mp3.constant.enums.db.LoginLog_failTypeEnum;
import com.cdfive.mp3.constant.enums.db.User_userStatusEnum;
import com.cdfive.mp3.mapper.UserMapper;
import com.cdfive.mp3.mapper.UserMapperCustom;
import com.cdfive.mp3.mapper.UserRoleMapperCustom;
import com.cdfive.mp3.po.User;
import com.cdfive.mp3.po.UserRole;
import com.cdfive.mp3.service.MenuService;
import com.cdfive.mp3.service.Mp3BaseService;
import com.cdfive.mp3.service.UserService;
import com.cdfive.mp3.vo.menu.MenuTreeNodeVo;
import com.cdfive.mp3.vo.session.SessionUserShowVo;
import com.cdfive.mp3.vo.session.SessionUserVo;
import com.cdfive.mp3.vo.user.UserDetailVo;
import com.cdfive.mp3.vo.user.UserListVo;

@Service
public class UserServiceImpl extends Mp3BaseService implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserMapperCustom userMapperCustom;
	@Autowired
	private UserRoleMapperCustom userRoleMapperCustom;
	@Autowired
	private MenuService menuService;

	@Override
	public User find(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> findList() {
		return null;
	}

	@Override
	public void login(String username, String password) {
		checkNotEmpty(username, "用户名");
		checkNotEmpty(password, "密码");
		User user = userMapperCustom.findByUserName(username);
		if (user == null) {
			logService.addLoginLog(null, username, null, YesNoEnum.NO.getCode(),
					LoginLog_failTypeEnum.PWD_ERR.getCode());
			failNotRollback("用户名或密码错误");
		}
		if (User_userStatusEnum.FROZEN.getCode().equals(user)) {
			logService.addLoginLog(user.getId(), username, user.getRealName(), YesNoEnum.NO.getCode(),
					LoginLog_failTypeEnum.FROZEN.getCode());
			failNotRollback("用户名已被冻结");
		}

		String enPassword = Md5Util.md5(password);
		if (!user.getPassword().equals(enPassword)) {
			logService.addLoginLog(user.getId(), username, user.getRealName(), YesNoEnum.NO.getCode(),
					LoginLog_failTypeEnum.PWD_ERR.getCode());
			Integer errCount = user.getErrCount();
			if (errCount == null) {
				errCount = 0;
			}
			errCount++;
			if (errCount < Mp3Constant.LOGIN_MAX_ERR_COUNT) {
				log.info("密码错误，还有" + (Mp3Constant.LOGIN_MAX_ERR_COUNT - errCount) + "次机会");
				user.setErrCount(errCount);
				user.setUpdateTime(now());
				userMapper.updateByPrimaryKeySelective(user);
				failNotRollback("用户名或密码错误");
			} else {
				log.info("密码错误超过" + Mp3Constant.LOGIN_MAX_ERR_COUNT + "次，账号被冻结");
				user.setErrCount(errCount);
				user.setUserStatus(User_userStatusEnum.FROZEN.getCode());
				user.setUpdateTime(now());
				userMapper.updateByPrimaryKeySelective(user);
				failNotRollback("密码错误超过" + Mp3Constant.LOGIN_MAX_ERR_COUNT + "次，账号被冻结");
			}
		}
		user.setErrCount(0);
		user.setUpdateTime(now());
		userMapper.updateByPrimaryKeySelective(user);

		SessionUserVo sessionUserVo = new SessionUserVo();
		sessionUserVo.setUserId(user.getId());
		sessionUserVo.setUserName(user.getUserName());
		sessionUserVo.setRealName(user.getRealName());
		setSessionUser(sessionUserVo);
		logService.addLoginLog(user.getId(), username, user.getRealName(), YesNoEnum.YES.getCode(), null);
		addBizLog("登录");
	}

	@Override
	public void logout() {
		addBizLog("退出");
		invalidateSession();
	}

	@Override
	public SessionUserShowVo userShow() {
		addBizLog("查询用户信息");
		SessionUserVo sessionUserVo = getSessionUser();
		SessionUserShowVo sessionUserShowVo = new SessionUserShowVo();
		sessionUserShowVo.setRealName(sessionUserVo.getRealName());
		List<MenuTreeNodeVo> userMenuList = menuService.findUserMenuList(sessionUserVo.getUserId());
		sessionUserShowVo.setMenus(userMenuList);
		return sessionUserShowVo;
	}

	@Override
	public JqGridResponse<UserListVo> findUserJqGridList(JqGridRequest request) {
		addBizLog("查询用户列表");
		int records = userMapperCustom.findUserJqGridListCount(request);
		List<UserListVo> rows = userMapperCustom.findUserJqGridList(request);
		JqGridResponse<UserListVo> jqGridList = new JqGridResponse<UserListVo>(request, records, rows);
		return jqGridList;
	}

	@Override
	public UserDetailVo findUserDetail(String id) {
		addBizLog("查询用户详情");
		checkNotEmpty(id, "编号");
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null) {
			fail("记录不存在");
		}
		if (StatusEnum.DELETED.getCode().equals(user.getStatus())) {
			fail("记录已删除");
		}
		UserDetailVo userDetailVo = new UserDetailVo();
		userDetailVo.setId(user.getId());
		userDetailVo.setUserName(user.getUserName());
		userDetailVo.setRealName(user.getRealName());
		userDetailVo.setIsAdmin(user.getIsAdmin());
		userDetailVo.setUserStatus(user.getUserStatus());
		userDetailVo.setErrCount(user.getErrCount());
		List<String> roleIds = userRoleMapperCustom.findRoleIdListByUserId(id);
		userDetailVo.setRoleIds(roleIds);
		return userDetailVo;
	}

	@Override
	public void addUser(String userName, String password, String realName, Integer isAdmin, List<String> roleIds) {
		addBizLog("新增用户");
		checkNotEmpty(userName, "用户名");
		checkNotEmpty(password, "密码");
		checkRange(isAdmin, new Integer[] { YesNoEnum.YES.getCode(), YesNoEnum.NO.getCode() });
		User tmpUser = userMapperCustom.findByUserName(userName);
		if (tmpUser != null) {
			fail("用户已存在");
		}

		User user = new User();
		user.setId(uuid());
		user.setUserName(userName);
		String enPassword = Md5Util.md5(password);
		user.setPassword(enPassword);
		user.setRealName(realName);
		user.setIsAdmin(isAdmin);
		user.setUserStatus(User_userStatusEnum.NORMAL.getCode());
		user.setErrCount(0);
		user.setCreateUserId(getSessionUserId());
		user.setUpdateUserId(getSessionUserId());
		user.setCreateTime(now());
		user.setUpdateTime(now());
		user.setStatus(StatusEnum.NORMAL.getCode());
		userMapper.insertSelective(user);
		if (isNotEmpty(roleIds)) {
			List<UserRole> userRoles = new ArrayList<UserRole>();
			for (String roleId : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setId(uuid());
				userRole.setUserId(user.getId());
				userRole.setRoleId(roleId);
				userRole.setCreateUserId(getSessionUserId());
				userRole.setUpdateUserId(getSessionUserId());
				userRole.setCreateTime(now());
				userRole.setUpdateTime(now());
				userRole.setStatus(StatusEnum.NORMAL.getCode());
				userRoles.add(userRole);
			}
			userRoleMapperCustom.batchInsert(userRoles);
		}
	}

	@Override
	public void updateUser(String id, String realName, Integer isAdmin, Integer userStatus, List<String> roleIds) {
		addBizLog("修改用户");
		checkNotEmpty(id, "编号");
		checkRange(isAdmin, new Integer[] { YesNoEnum.YES.getCode(), YesNoEnum.NO.getCode() });
		checkRange(userStatus,
				new Integer[] { User_userStatusEnum.NORMAL.getCode(), User_userStatusEnum.FROZEN.getCode() });
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null) {
			fail("记录不存在");
		}
		if (StatusEnum.DELETED.getCode().equals(user.getStatus())) {
			fail("记录已删除");
		}
		user.setRealName(realName);
		user.setIsAdmin(isAdmin);
		user.setUserStatus(userStatus);
		user.setUpdateUserId(getSessionUserId());
		user.setUpdateTime(now());
		userMapper.updateByPrimaryKey(user);
		userRoleMapperCustom.deleteByUserId(id);
		if (isNotEmpty(roleIds)) {
			List<UserRole> userRoles = new ArrayList<UserRole>();
			for (String roleId : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setId(uuid());
				userRole.setUserId(user.getId());
				userRole.setRoleId(roleId);
				userRole.setCreateUserId(getSessionUserId());
				userRole.setUpdateUserId(getSessionUserId());
				userRole.setCreateTime(now());
				userRole.setUpdateTime(now());
				userRole.setStatus(StatusEnum.NORMAL.getCode());
				userRoles.add(userRole);
			}
			userRoleMapperCustom.batchInsert(userRoles);
		}
	}

	@Override
	public void updateUserRole(String id, List<String> roleIds) {
		addBizLog("修改用户角色");
		checkNotEmpty(id, "编号");
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null) {
			fail("记录不存在");
		}
		if (StatusEnum.DELETED.getCode().equals(user.getStatus())) {
			fail("记录已删除");
		}
		if (isNotEmpty(roleIds)) {
			List<UserRole> userRoles = new ArrayList<UserRole>();
			for (String roleId : roleIds) {
				UserRole userRole = new UserRole();
				userRole.setId(uuid());
				userRole.setUserId(user.getId());
				userRole.setRoleId(roleId);
				userRole.setCreateUserId(getSessionUserId());
				userRole.setUpdateUserId(getSessionUserId());
				userRole.setCreateTime(now());
				userRole.setUpdateTime(now());
				userRole.setStatus(StatusEnum.NORMAL.getCode());
				userRoles.add(userRole);
			}
			userRoleMapperCustom.batchInsert(userRoles);
		}
	}

	@Override
	public void delUser(String id) {
		addBizLog("删除用户");
		checkNotEmpty(id, "编号");
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null) {
			fail("记录不存在");
		}
		if (StatusEnum.DELETED.getCode().equals(user.getStatus())) {
			fail("记录已删除");
		}
		user.setStatus(StatusEnum.DELETED.getCode());
		user.setUpdateUserId(getSessionUserId());
		user.setUpdateTime(now());
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword, String confirmPassword) {
		addBizLog("修改密码");
		checkNotEmpty(oldPassword, "原密码");
		checkNotEmpty(newPassword, "新密码");
		checkNotEmpty(confirmPassword, "确认密码");
		if (!newPassword.equals(confirmPassword)) {
			fail("新密码和确认密码不一致");
		}
		String userId = getSessionUserId();
		User user = userMapper.selectByPrimaryKey(userId);
		String enPassword = Md5Util.md5(oldPassword);
		if (!user.getPassword().equals(enPassword)) {
			fail("原密码错误");
		}
		String newEnPassword = Md5Util.md5(newPassword);
		user.setPassword(newEnPassword);
		user.setUpdateTime(now());
		userMapper.updateByPrimaryKeySelective(user);
	}

}
