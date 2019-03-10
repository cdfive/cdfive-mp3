package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.constant.Mp3UriConstant;
import com.cdfive.mp3.service.Mp3Service;
import com.cdfive.mp3.vo.song.SongAllVo;
import com.cdfive.mp3.vo.song.SongJqGridListVo;
import com.cdfive.mp3.vo.song.SongListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class Mp3Controller extends BaseController {
	
	@Autowired
	private Mp3Service songService;
	
	@RequestMapping(Mp3UriConstant.MP3_LIST)
	@ResponseBody
	public JqGridResponse<SongJqGridListVo> findSongJqGridList(JqGridRequest request) {
		return songService.findSongJqGridList(request);
	}
	
	@RequestMapping(Mp3UriConstant.MP3_ADD)
	@ResponseBody
	public BaseResponse<?> add(String songName, String author, String groups, String reason, String mp3Path, String lrcPath, String krcPath) {
		songService.addSong(songName, author, groups, reason, mp3Path, lrcPath, krcPath);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.MP3_UPDATE)
	@ResponseBody
	public BaseResponse<?> update(String id, String songName, String author, String groups, String reason, String mp3Path, String lrcPath, String krcPath) {
		songService.updateSong(id, songName, author, groups, reason, mp3Path, lrcPath, krcPath);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.MP3_DEL)
	@ResponseBody
	public BaseResponse<?> del(String id) {
		songService.delSong(id);
		return succ();
	}
	
	@RequestMapping(Mp3UriConstant.MP3_ALL)
	@ResponseBody
	public BaseResponse<SongAllVo> all() {
		SongAllVo songAllVo = songService.findSongAll();
		return succ(songAllVo);
	}
	
	@RequestMapping(Mp3UriConstant.MP3_TOP_LIST)
	@ResponseBody
	public BaseResponse<List<SongJqGridListVo>> topList(Integer num) {
		List<SongJqGridListVo> list = songService.findSongTopList(num);
		return succ(list);
	}
	
	@RequestMapping(Mp3UriConstant.MP3_RANDOM_LIST)
	@ResponseBody
	public BaseResponse<List<SongListVo>> randomList() {
		List<SongListVo> list = songService.findSongRandomList(20);
		return succ(list);
	}
	
	@RequestMapping(Mp3UriConstant.MP3_PLAY)
	@ResponseBody
	public BaseResponse<Integer> play(String id) {
		Integer play = songService.play(id);
		return succ(play);
	}
}
