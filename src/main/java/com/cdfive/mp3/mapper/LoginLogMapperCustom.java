package com.cdfive.mp3.mapper;

import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.mp3.vo.log.LoginLogListVo;

import java.util.List;

public interface LoginLogMapperCustom {
	int findLoginLogJqGridListCount(JqGridRequest request);
    
    List<LoginLogListVo> findLoginLogJqGridList(JqGridRequest request);
}