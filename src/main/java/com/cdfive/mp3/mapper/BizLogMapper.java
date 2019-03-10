package com.cdfive.mp3.mapper;

import com.cdfive.mp3.po.BizLog;

public interface BizLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(BizLog record);

    int insertSelective(BizLog record);

    BizLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BizLog record);

    int updateByPrimaryKey(BizLog record);
}