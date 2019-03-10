package com.cdfive.mp3.mapper;

import com.cdfive.mp3.po.Upload;

public interface UploadMapper {
    int deleteByPrimaryKey(String id);

    int insert(Upload record);

    int insertSelective(Upload record);

    Upload selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Upload record);

    int updateByPrimaryKey(Upload record);
}