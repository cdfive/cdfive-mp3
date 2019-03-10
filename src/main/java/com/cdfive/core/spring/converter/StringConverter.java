package com.cdfive.core.spring.converter;

import org.springframework.core.convert.converter.Converter;

public class StringConverter implements Converter<String, String> {

	public String convert(String source) {
		try {
			if(source!=null){
				source = source.trim();
				if(source.equals("")){
					return null;
				}
			}
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return source;
	}

}
