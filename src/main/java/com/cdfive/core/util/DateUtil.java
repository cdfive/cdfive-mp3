package com.cdfive.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtil {

	public static Date now() {
		return new Date();
	}

	public static double beforeNowSeconds(Date d) {
		Date now = new Date();
		double seconds = (now.getTime() - d.getTime()) / 1000.0;
		return seconds;
	}

	public static String formateDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static boolean isDayBeforeNow(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(now());
		int day2 = calendar.get(Calendar.DAY_OF_YEAR);
		return (day1 - day2) < 0;
	}
}
