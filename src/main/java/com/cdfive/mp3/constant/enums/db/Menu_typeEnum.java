package com.cdfive.mp3.constant.enums.db;

/**
 * 菜单类型
 */
public enum Menu_typeEnum {
	MENU(1, "菜单"), BUTTON(2, "按钮");
	
	private Integer code;
	private String label;

	Menu_typeEnum(Integer code, String label) {
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
