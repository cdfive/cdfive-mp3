package com.cdfive.mp3.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdfive.core.vo.common.NameCountVo;
import com.cdfive.mp3.mapper.ArticleMapperCustom;
import com.cdfive.mp3.mapper.CatalogMapperCustom;
import com.cdfive.mp3.mapper.RequestLogMapperCustom;
import com.cdfive.mp3.mapper.SongMapperCustom;
import com.cdfive.mp3.mapper.UserMapperCustom;
import com.cdfive.mp3.service.DashboardService;
import com.cdfive.mp3.service.Mp3BaseService;
import com.cdfive.mp3.vo.dashboard.DashBoardCountVo;

@Service
public class DashboardServiceImpl extends Mp3BaseService implements DashboardService {

	@Autowired
	private RequestLogMapperCustom requestLogMapperCustom;
	@Autowired
	private SongMapperCustom songMapperCustom;
	@Autowired
	private CatalogMapperCustom catalogMapperCustom;
	@Autowired
	private ArticleMapperCustom articleMapperCustom;
	@Autowired
	private UserMapperCustom userMapperCustom;
	
	@Override
	public List<NameCountVo> findRequestLogStat(String date, String ip) {
		List<NameCountVo> list = Collections.emptyList();
		if (date == null && ip == null) {
			list = requestLogMapperCustom.findDateStat();
		} else if (date != null && ip == null) {
			list = requestLogMapperCustom.findDateIpStat(date);
		} else if (date != null && ip != null) {
			list = requestLogMapperCustom.findDateIpUriStat(date, ip);
		} else {
			failPermissionDeny();
		}
		return list;
	}

	@Override
	public DashBoardCountVo findDashBoardCount() {
		int mp3Count = songMapperCustom.findCount();
		int catalogCount = catalogMapperCustom.findCount();
		int articleCount = articleMapperCustom.findCount();
		int userCount = userMapperCustom.findCount();
		DashBoardCountVo dashBoardCountVo = new DashBoardCountVo();
		dashBoardCountVo.setMp3Count(mp3Count);
		dashBoardCountVo.setCatalogCount(catalogCount);
		dashBoardCountVo.setArticleCount(articleCount);
		dashBoardCountVo.setUserCount(userCount);
		return dashBoardCountVo;
	}
	
}
