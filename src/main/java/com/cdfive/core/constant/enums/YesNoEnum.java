package com.cdfive.core.constant.enums;

public enum YesNoEnum {
	YES(1, "是"), NO(0, "否");
	
	private Integer code;
	private String label;

	YesNoEnum(Integer code, String label) {
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
