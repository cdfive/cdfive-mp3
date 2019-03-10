package com.cdfive.mp3.mapper;

import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.mp3.vo.song.SongJqGridListVo;
import com.cdfive.mp3.vo.song.SongListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SongMapperCustom {
	
	int findCount();
	
	int findSongJqGridListCount(JqGridRequest request);
	
    List<SongJqGridListVo> findSongJqGridList(JqGridRequest request);
    
    int findCountByFullName(@Param("fullName") String fullName);
    
    int findMaxSort();
    
    List<SongListVo> findSongListByDigit(@Param("digit") Integer digit);
    
    List<SongListVo> findSongListByGroups(@Param("groups") String groups);
    
    List<SongJqGridListVo> findSongTopList(@Param("num") Integer num);
    
    List<SongListVo> findSongRandomList(@Param("num") Integer num);
}