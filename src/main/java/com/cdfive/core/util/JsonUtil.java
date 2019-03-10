package com.cdfive.core.util;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

	public static <T> T fromJson(String json, Class<T> resultClass) {
		return JSON.parseObject(json, resultClass);
	}

	public static String toJson(Object object) {
		return JSON.toJSONString(object);
	}
}
