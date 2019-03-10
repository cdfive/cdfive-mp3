package com.cdfive.mp3.vo.user;

import java.util.List;

public class UserDetailVo {
	private String id;
	private String userName;
	private String realName;
	private Integer isAdmin;
	private Integer userStatus;
	private Integer errCount;
	private List<String> roleIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getErrCount() {
		return errCount;
	}

	public void setErrCount(Integer errCount) {
		this.errCount = errCount;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

}
