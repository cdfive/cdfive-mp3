package com.cdfive.mp3.vo.log;

import java.util.Date;

public class LoginLogListVo {
	private String id;

    private String userId;

    private String userName;

    private String realName;

    private Integer isSucc;

    private Integer failType;

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

	public Integer getIsSucc() {
		return isSucc;
	}

	public void setIsSucc(Integer isSucc) {
		this.isSucc = isSucc;
	}

	public Integer getFailType() {
		return failType;
	}

	public void setFailType(Integer failType) {
		this.failType = failType;
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
