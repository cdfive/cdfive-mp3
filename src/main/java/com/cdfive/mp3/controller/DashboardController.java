package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.vo.common.NameCountVo;
import com.cdfive.mp3.constant.Mp3UriConstant;
import com.cdfive.mp3.service.DashboardService;
import com.cdfive.mp3.vo.dashboard.DashBoardCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("")
@Controller
public class DashboardController extends BaseController {
	@Autowired
	private DashboardService dashboardService;
	
	@RequestMapping(Mp3UriConstant.STAT_REQUEST)
	@ResponseBody
	public BaseResponse<List<NameCountVo>> statRequest(String date, String ip) {
		List<NameCountVo> list = dashboardService.findRequestLogStat(date, ip);
		return succ(list);
	}
	
	@RequestMapping(Mp3UriConstant.STAT_COUNT)
	@ResponseBody
	public BaseResponse<DashBoardCountVo> statCount() {
		DashBoardCountVo dashBoardCountVo = dashboardService.findDashBoardCount();
		return succ(dashBoardCountVo);
	}
	
}
