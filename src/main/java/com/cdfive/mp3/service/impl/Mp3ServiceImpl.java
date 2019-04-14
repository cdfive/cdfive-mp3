package com.cdfive.mp3.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.util.FileUtil;
import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.mapper.SongMapper;
import com.cdfive.mp3.mapper.SongMapperCustom;
import com.cdfive.mp3.po.Song;
import com.cdfive.mp3.service.Mp3BaseService;
import com.cdfive.mp3.service.Mp3Service;
import com.cdfive.mp3.vo.song.SongAllVo;
import com.cdfive.mp3.vo.song.SongJqGridListVo;
import com.cdfive.mp3.vo.song.SongListVo;

@Service
public class Mp3ServiceImpl extends Mp3BaseService implements Mp3Service {

	@Autowired
	private SongMapper songMapper;
	@Autowired
	private SongMapperCustom songMapperCustom;
	
	@Override
	public JqGridResponse<SongJqGridListVo> findSongJqGridList(JqGridRequest request) {
		addBizLog("查询mp3列表");
		int records = songMapperCustom.findSongJqGridListCount(request);
		List<SongJqGridListVo> rows = songMapperCustom.findSongJqGridList(request);
		JqGridResponse<SongJqGridListVo> jqGrid = new JqGridResponse<SongJqGridListVo>(request, records, rows);
		return jqGrid;
	}

	@Override
	public void addSong(String songName, String author, String groups, String reason, String mp3Path,
			String lrcPath, String krcPath) {
		addBizLog("新增mp3");
		checkNotEmpty(mp3Path, "请上传mp3文件");
		checkNotEmpty(lrcPath, "请上传lrc文件");
		checkNotEmpty(krcPath, "请上传krc文件");
		
		String fullName;
		if (StringUtils.isEmpty(songName) && StringUtils.isEmpty(author)) {
			String oriFileName = mp3Path;
			String oriFileNameWithoutExt = FileUtil.getFileNameWithoutExt(oriFileName);
			fullName = oriFileNameWithoutExt;
			String[] tmpNames = oriFileNameWithoutExt.split("-");
			songName = tmpNames[0].trim();
			author = tmpNames[1].trim();
		} else {
			fullName = songName + " - " + author;
		}
		
		int tmpCount = songMapperCustom.findCountByFullName(fullName);
		if (tmpCount > 0) {
			fail("歌名已存在");
		}
		
		int maxSort = songMapperCustom.findMaxSort();
		maxSort++;
		
		Song song = new Song();
		song.setId(uuid());
		song.setSongName(songName);
		song.setFullName(fullName);
		song.setAuthor(author);
		song.setPath(mp3Path);
		song.setDigit(songName.length());
		song.setGroups(groups);
		song.setReason(reason);
		song.setPlayCount(0);
		song.setSort(maxSort);
		song.setCreateUserId(getSessionUserId());
		song.setUpdateUserId(getSessionUserId());
		song.setCreateTime(now());
		song.setUpdateTime(now());
		song.setStatus(StatusEnum.NORMAL.getCode());
		songMapper.insertSelective(song);
	}

	@Override
	public void updateSong(String id, String songName, String author, String groups, String reason,
			String mp3Path, String lrcPath, String krcPath) {
		addBizLog("修改mp3");
		Song song = checkSong(id);
		
		checkNotEmpty(mp3Path, "请上传mp3文件");
		checkNotEmpty(lrcPath, "请上传lrc文件");
		checkNotEmpty(krcPath, "请上传krc文件");
		
		String fullName;
		if (StringUtils.isEmpty(songName) && StringUtils.isEmpty(author)) {
			String oriFileName = mp3Path;
			String oriFileNameWithoutExt = FileUtil.getFileNameWithoutExt(oriFileName);
			fullName = oriFileNameWithoutExt;
			String[] tmpNames = oriFileNameWithoutExt.split("-");
			songName = tmpNames[0].trim();
			author = tmpNames[1].trim();
		} else {
			fullName = songName + " - " + author;
		}
		
		if (song == null) {
			fail("歌不存在");
		}
		
		if (!song.getFullName().equals(fullName)) {
			int tmpCount = songMapperCustom.findCountByFullName(fullName);
			if (tmpCount > 0) {
				fail("歌名已存在");
			}
		}
		
		song.setSongName(songName);
		song.setFullName(fullName);
		song.setAuthor(author);
		song.setPath(mp3Path);
		song.setDigit(songName.length());
		song.setGroups(groups);
		song.setReason(reason);
		song.setUpdateUserId(getSessionUserId());
		song.setUpdateTime(now());
		songMapper.updateByPrimaryKey(song);
	}

	@Override
	public void delSong(String id) {
		addBizLog("删除mp3");
		checkNotEmpty(id, "编号");
		Song song = songMapper.selectByPrimaryKey(id);
		if (song == null) {
			fail("歌不存在");
		}
		song.setStatus(StatusEnum.DELETED.getCode());
		song.setUpdateTime(now());
		songMapper.updateByPrimaryKeySelective(song);
	}

	@Override
	public SongAllVo findSongAll() {
		addBizLog("查询mp3首页列表");
		SongAllVo songAllVo = new SongAllVo();
		
		songAllVo.setD2018(songMapperCustom.findSongListByGroups("2018"));
		songAllVo.setD2017(songMapperCustom.findSongListByGroups("2017"));
		songAllVo.setD2016(songMapperCustom.findSongListByGroups("2016"));
		
		songAllVo.setD1(songMapperCustom.findSongListByDigit(1));
		songAllVo.setD2(songMapperCustom.findSongListByDigit(2));
		songAllVo.setD3(songMapperCustom.findSongListByDigit(3));
		songAllVo.setD4(songMapperCustom.findSongListByDigit(4));
		songAllVo.setD5(songMapperCustom.findSongListByDigit(5));
		
		songAllVo.setdYu(songMapperCustom.findSongListByGroups("鱼"));
		songAllVo.setdLiZhi(songMapperCustom.findSongListByGroups("励志"));
		songAllVo.setdAiQing(songMapperCustom.findSongListByGroups("爱情"));
		songAllVo.setdWenNuan(songMapperCustom.findSongListByGroups("温暖"));
		songAllVo.setdMan(songMapperCustom.findSongListByGroups("Man"));
		
		songAllVo.setdEn(songMapperCustom.findSongListByGroups("英语"));
		songAllVo.setdYy(songMapperCustom.findSongListByGroups("粤语"));
		songAllVo.setdWt(songMapperCustom.findSongListByGroups("无题"));
		songAllVo.setdDqxh(songMapperCustom.findSongListByGroups("单曲循环"));
		songAllVo.setdSpec(songMapperCustom.findSongListByGroups("特别"));
		
		return songAllVo;
	}

	@Override
	public List<SongJqGridListVo> findSongTopList(Integer num) {
		addBizLog("查询mp3首页列表");
		checkNotEmpty(num, "数量");
		if (num < 0) {
			fail("数量范围1-10");
		}
		if (num > 10) {
			num = 10;
		}
		List<SongJqGridListVo> list = songMapperCustom.findSongTopList(num);
		return list;
	}
	
	@Override
	public List<SongListVo> findSongRandomList(Integer num) {
		addBizLog("查询mp3随机列表");
		checkNotEmpty(num, "数量");
		if (num < 0) {
			fail("数量范围1-20");
		}
		if (num > 20) {
			num = 20;
		}
		List<SongListVo> list = songMapperCustom.findSongRandomList(num);
		return list;
	}

	@Override
	public Integer play(String id) {
		addBizLog("播放mp3");
		Song song = checkSong(id);
		Integer playCount = song.getPlayCount();
		playCount = playCount == null ? 1 : (playCount + 1);
		song.setPlayCount(playCount);
		song.setUpdateTime(now());
		songMapper.updateByPrimaryKeySelective(song);
		return playCount;
	}
	
	private Song checkSong(String id) {
		checkNotEmpty(id, "编号");
		Song song = songMapper.selectByPrimaryKey(id);
		if (song == null) {
			fail("记录不存在");
		}
		if (StatusEnum.DELETED.getCode().equals(song.getStatus())) {
			fail("记录已删除");
		}
		return song;
	}
}
