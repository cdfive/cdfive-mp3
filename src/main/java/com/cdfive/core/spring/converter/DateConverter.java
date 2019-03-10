package com.cdfive.core.spring.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

	public Date convert(String date) {

		try {

			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			return null;

		}

	}

}
