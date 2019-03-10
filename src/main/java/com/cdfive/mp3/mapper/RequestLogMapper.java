package com.cdfive.mp3.mapper;

import com.cdfive.mp3.po.RequestLog;

public interface RequestLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(RequestLog record);

    int insertSelective(RequestLog record);

    RequestLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RequestLog record);

    int updateByPrimaryKey(RequestLog record);
}