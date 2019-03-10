package com.cdfive.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtil {
	
	private static final String REG_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
	private static final String REG_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
	private static final String REG_HTML = "<[^>]+>";
	
	public static String delHTMLTag(String htmlStr) {
		Pattern pScript = Pattern.compile(REG_SCRIPT, Pattern.CASE_INSENSITIVE);
		Matcher mScript = pScript.matcher(htmlStr);
		htmlStr = mScript.replaceAll("");

		Pattern pStyle = Pattern.compile(REG_STYLE, Pattern.CASE_INSENSITIVE);
		Matcher mStyle = pStyle.matcher(htmlStr);
		htmlStr = mStyle.replaceAll("");

		Pattern pHtml = Pattern.compile(REG_HTML, Pattern.CASE_INSENSITIVE);
		Matcher mHtml = pHtml.matcher(htmlStr);
		htmlStr = mHtml.replaceAll("");

		return htmlStr.trim();
	}
}
