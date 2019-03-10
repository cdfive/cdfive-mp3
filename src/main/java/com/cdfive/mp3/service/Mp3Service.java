package com.cdfive.mp3.service;

import java.util.List;

import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.vo.song.SongAllVo;
import com.cdfive.mp3.vo.song.SongJqGridListVo;
import com.cdfive.mp3.vo.song.SongListVo;

public interface Mp3Service {
	
	JqGridResponse<SongJqGridListVo> findSongJqGridList(JqGridRequest request);
	
	void addSong(String songName, String author, String groups, String reason, String mp3Path, String lrcPath, String krcPath);
	
	void updateSong(String id, String songName, String author, String groups, String reason, String mp3Path, String lrcPath, String krcPath);
	
	void delSong(String id);
	
	SongAllVo findSongAll();
	
	List<SongJqGridListVo> findSongTopList(Integer num);
	
	List<SongListVo> findSongRandomList(Integer num);
	
	Integer play(String id);
	
}
