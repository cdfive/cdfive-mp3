package com.cdfive.mp3.mapper;

import com.cdfive.mp3.po.Song;

public interface SongMapper {
    int deleteByPrimaryKey(String id);

    int insert(Song record);

    int insertSelective(Song record);

    Song selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Song record);

    int updateByPrimaryKey(Song record);
}