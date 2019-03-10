package com.cdfive.mp3.service;

import java.util.List;

import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.po.RequestLog;
import com.cdfive.mp3.vo.log.ApiLogListVo;
import com.cdfive.mp3.vo.log.BizLogListVo;
import com.cdfive.mp3.vo.log.LoginLogListVo;

public interface LogService {
	void addRequestLog(RequestLog requestLog);
	
	JqGridResponse<LoginLogListVo> findLoginLogJqGridList(JqGridRequest request);
	
	void addLoginLog(String userId, String userName, String realName, Integer isSucc, Integer failType);
	
	JqGridResponse<BizLogListVo> findBizLogJqGridList(JqGridRequest request);
	
	void addBizLog(String operKey);
	
	void addBizLog(String operKey, String operDescription);
	
	List<ApiLogListVo> findApiLogTopList(Integer num);
}
