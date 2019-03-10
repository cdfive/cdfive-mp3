package com.cdfive.core.spring.aspect;

public enum ApiResultEnum {
	SUCC(1, "成功"), BUSSINESS_FAIL(2, "业务失败"), OTHER_FAIL(3, "其它失败"), RETURN_FAIL(4, "返回失败"), UNINVOKE(5, "未调用");
	private Integer code;
	private String label;

	ApiResultEnum(int code, String label) {
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
