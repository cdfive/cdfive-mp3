package com.cdfive.mp3.mapper;

import com.cdfive.mp3.vo.log.ApiLogListVo;

import java.util.List;


public interface ApiLogMapperCustom {
    List<ApiLogListVo> findApiLogTopList(Integer num);
}