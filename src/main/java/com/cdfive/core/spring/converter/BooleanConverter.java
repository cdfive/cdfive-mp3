package com.cdfive.core.spring.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

public class BooleanConverter implements Converter<String, Boolean> {

    private static final Set<String> trueValues = new HashSet<String>(4);

    private static final Set<String> falseValues = new HashSet<String>(4);

    static {
        trueValues.add("true");
        trueValues.add("on");
        trueValues.add("yes");
        trueValues.add("1");

        falseValues.add("false");
        falseValues.add("off");
        falseValues.add("no");
        falseValues.add("0");
    }

    @Override
    public Boolean convert(String source) {
        // TODO Auto-generated method stub
        String value = source.trim();
        if ("".equals(value)) {
            return Boolean.FALSE;
        }
        value = value.toLowerCase();
        if (trueValues.contains(value)) {
            return Boolean.TRUE;
        } else if (falseValues.contains(value)) {
            return Boolean.FALSE;
        } else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
    }

}
