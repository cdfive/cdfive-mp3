package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.constant.Mp3UriConstant;
import com.cdfive.mp3.service.LogService;
import com.cdfive.mp3.vo.log.ApiLogListVo;
import com.cdfive.mp3.vo.log.BizLogListVo;
import com.cdfive.mp3.vo.log.LoginLogListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("")
@Controller
public class LogController extends BaseController {
	@Autowired
	private LogService logService;
	
	@RequestMapping(Mp3UriConstant.LOGIN_LOG_LIST)
	@ResponseBody
	public JqGridResponse<LoginLogListVo> findLoginLogJqGridList(JqGridRequest request) {
		JqGridResponse<LoginLogListVo> jqList = logService.findLoginLogJqGridList(request);
		return jqList;
	}
	
	@RequestMapping(Mp3UriConstant.BIZ_LOG_LIST)
	@ResponseBody
	public JqGridResponse<BizLogListVo> findBizLogJqGridList(JqGridRequest request) {
		JqGridResponse<BizLogListVo> jqList = logService.findBizLogJqGridList(request);
		return jqList;
	}
	
	@RequestMapping(Mp3UriConstant.API_LOG_TOP_LIST)
	@ResponseBody
	public BaseResponse<List<ApiLogListVo>> apiLogTopList(Integer num) {
		List<ApiLogListVo> list = logService.findApiLogTopList(num);
		return succ(list);
	}
}
