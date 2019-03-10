package com.cdfive.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class CommonUtil {
	public static String uuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public static String maxString(String str, int len) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		if (str.length() >= len) {
			return str.substring(0, len);
		} else {
			return str;
		}
	}
}
