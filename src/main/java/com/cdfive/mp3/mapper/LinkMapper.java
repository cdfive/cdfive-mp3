package com.cdfive.mp3.mapper;

import com.cdfive.mp3.po.Link;

public interface LinkMapper {
    int deleteByPrimaryKey(String id);

    int insert(Link record);

    int insertSelective(Link record);

    Link selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Link record);

    int updateByPrimaryKey(Link record);
}