package com.cdfive.mp3.vo.session;

import java.io.Serializable;

public class SessionUserVo implements Serializable {
	private static final long serialVersionUID = -450378979482635452L;

	private String userId;
	private String userName;
	private String realName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

}
