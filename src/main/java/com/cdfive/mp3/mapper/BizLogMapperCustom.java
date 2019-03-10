package com.cdfive.mp3.mapper;

import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.mp3.vo.log.BizLogListVo;

import java.util.List;

public interface BizLogMapperCustom {
	int findBizLogJqGridListCount(JqGridRequest request);
    
    List<BizLogListVo> findBizLogJqGridList(JqGridRequest request);
}