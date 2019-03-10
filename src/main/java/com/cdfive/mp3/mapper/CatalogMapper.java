package com.cdfive.mp3.mapper;

import com.cdfive.mp3.po.Catalog;

public interface CatalogMapper {
    int deleteByPrimaryKey(String id);

    int insert(Catalog record);

    int insertSelective(Catalog record);

    Catalog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Catalog record);

    int updateByPrimaryKey(Catalog record);
}