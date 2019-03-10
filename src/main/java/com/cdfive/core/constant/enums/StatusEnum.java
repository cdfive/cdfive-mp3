package com.cdfive.core.constant.enums;

public enum StatusEnum {
	NORMAL(1, "正常"), DELETED(0, "已删除");
	
	private Integer code;
	private String label;

	StatusEnum(Integer code, String label) {
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
