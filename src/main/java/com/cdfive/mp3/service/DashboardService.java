package com.cdfive.mp3.service;

import java.util.List;

import com.cdfive.core.vo.common.NameCountVo;
import com.cdfive.mp3.vo.dashboard.DashBoardCountVo;

public interface DashboardService {
	List<NameCountVo> findRequestLogStat(String date, String ip);
	
	DashBoardCountVo findDashBoardCount();
}
