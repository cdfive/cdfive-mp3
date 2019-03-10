package com.cdfive.mp3.controller;

import com.cdfive.core.base.BaseController;
import com.cdfive.core.base.BaseResponse;
import com.cdfive.mp3.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("")
@Controller
public class InitController extends BaseController {
	
	@Autowired
	private InitService initService;
	
	@RequestMapping("/v1/init2017")
	@ResponseBody
	public BaseResponse<?> init() {
		initService.init();
		return succ();
	}
	
}
