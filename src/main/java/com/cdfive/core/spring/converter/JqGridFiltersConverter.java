package com.cdfive.core.spring.converter;

import com.cdfive.core.util.JsonUtil;
import com.cdfive.core.vo.jqgrid.JqGridFilters;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class JqGridFiltersConverter implements Converter<String, JqGridFilters> {

    @Override
    public JqGridFilters convert(String source) {
        if (StringUtils.isNotEmpty(source)) {
            return JsonUtil.fromJson(source, JqGridFilters.class);
        }
        return null;
    }

}
