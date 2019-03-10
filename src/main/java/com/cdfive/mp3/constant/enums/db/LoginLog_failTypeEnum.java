package com.cdfive.mp3.constant.enums.db;

/**
 * 登录日志-失败类型
 */
public enum LoginLog_failTypeEnum {
	PWD_ERR(1, "密码错误"), FROZEN(2, "冻结");
	
	private Integer code;
	private String label;

	LoginLog_failTypeEnum(Integer code, String label) {
		this.code = code;
		this.label = label;
	}

	public Integer getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}	
}
