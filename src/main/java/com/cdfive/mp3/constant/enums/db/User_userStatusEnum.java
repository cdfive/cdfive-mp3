package com.cdfive.mp3.constant.enums.db;

/**
 * 用户状态
 */
public enum User_userStatusEnum {
	NORMAL(1, "正常"), FROZEN(2, "冻结");
	
	private Integer code;
	private String label;

	User_userStatusEnum(Integer code, String label) {
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
