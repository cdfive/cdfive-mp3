package com.cdfive.mp3.constant;

import com.cdfive.core.util.PropUtil;

public class Mp3PropConstant {
	private static String FILE_UPLOAD_PATH = "file_upload_path";
	private static String CATALOG_FUNNY_ID = "catalog_funny_id";
	private static String CATALOG_JUZIMI_ID = "catalog_juzimi_id";
	
	public static String file_upload_path() {
		return PropUtil.readProp(FILE_UPLOAD_PATH);
	}
	
	public static String catalog_funny_id() {
		return PropUtil.readProp(CATALOG_FUNNY_ID);
	}
	
	public static String catalog_juzimi_id() {
		return PropUtil.readProp(CATALOG_JUZIMI_ID);
	}
}