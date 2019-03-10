package com.cdfive.mp3.vo.log;

import java.util.Date;

public class BizLogListVo {
	private String id;

	private String userId;

	private String userName;

	private String realName;

	private String operKey;

	private String operDescription;

	private String ip;

	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getOperKey() {
		return operKey;
	}

	public void setOperKey(String operKey) {
		this.operKey = operKey;
	}

	public String getOperDescription() {
		return operDescription;
	}

	public void setOperDescription(String operDescription) {
		this.operDescription = operDescription;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
