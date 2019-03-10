package com.cdfive.mp3.mapper;

import com.cdfive.mp3.po.ApiLog;

public interface ApiLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApiLog record);

    int insertSelective(ApiLog record);

    ApiLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApiLog record);

    int updateByPrimaryKey(ApiLog record);
}