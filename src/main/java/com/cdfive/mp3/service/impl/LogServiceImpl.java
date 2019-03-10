package com.cdfive.mp3.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.constant.enums.YesNoEnum;
import com.cdfive.core.util.WebUtil;
import com.cdfive.core.vo.jqgrid.JqGridRequest;
import com.cdfive.core.vo.jqgrid.JqGridResponse;
import com.cdfive.mp3.constant.Mp3Constant;
import com.cdfive.mp3.mapper.ApiLogMapperCustom;
import com.cdfive.mp3.mapper.BizLogMapper;
import com.cdfive.mp3.mapper.BizLogMapperCustom;
import com.cdfive.mp3.mapper.LoginLogMapper;
import com.cdfive.mp3.mapper.LoginLogMapperCustom;
import com.cdfive.mp3.mapper.RequestLogMapper;
import com.cdfive.mp3.po.BizLog;
import com.cdfive.mp3.po.LoginLog;
import com.cdfive.mp3.po.RequestLog;
import com.cdfive.mp3.service.LogService;
import com.cdfive.mp3.service.Mp3BaseService;
import com.cdfive.mp3.vo.log.ApiLogListVo;
import com.cdfive.mp3.vo.log.BizLogListVo;
import com.cdfive.mp3.vo.log.LoginLogListVo;
import com.cdfive.mp3.vo.session.SessionUserVo;

@Service
public class LogServiceImpl extends Mp3BaseService implements LogService {

	@Autowired
	private RequestLogMapper requestLogMapper;
	@Autowired
	private LoginLogMapper loginLogMapper;
	@Autowired
	private LoginLogMapperCustom loginLogMapperCustom;
	@Autowired
	private BizLogMapper bizLogMapper;
	@Autowired
	private BizLogMapperCustom bizLogMapperCustom;
	@Autowired
	private ApiLogMapperCustom apiLogMapperCustom;
	
	@Override
	public void addRequestLog(RequestLog requestLog) {
		requestLogMapper.insertSelective(requestLog);
	}
	
	@Override
	public JqGridResponse<LoginLogListVo> findLoginLogJqGridList(JqGridRequest request) {
		int records = loginLogMapperCustom.findLoginLogJqGridListCount(request);
		List<LoginLogListVo> rows = loginLogMapperCustom.findLoginLogJqGridList(request);
		JqGridResponse<LoginLogListVo> jqGridList = new JqGridResponse<LoginLogListVo>(request, records, rows);
		return jqGridList;
	}

	@Override
	public void addLoginLog(String userId, String userName, String realName, Integer isSucc, Integer failType) {
		LoginLog loginLog = new LoginLog();
		loginLog.setId(uuid());
		loginLog.setUserId(userId);
		loginLog.setUserName(userName);
		loginLog.setRealName(realName);
		loginLog.setIsSucc(isSucc);
		if (YesNoEnum.NO.getCode().equals(isSucc)) {
			loginLog.setFailType(failType);
		}
		loginLog.setIp(WebUtil.getIp());
		loginLog.setCreateTime(now());
		loginLog.setUpdateTime(now());
		loginLog.setStatus(StatusEnum.NORMAL.getCode());
		loginLogMapper.insertSelective(loginLog);
	}
	
	@Override
	public JqGridResponse<BizLogListVo> findBizLogJqGridList(JqGridRequest request) {
		int records = bizLogMapperCustom.findBizLogJqGridListCount(request);
		List<BizLogListVo> rows = bizLogMapperCustom.findBizLogJqGridList(request);
		JqGridResponse<BizLogListVo> jqGridList = new JqGridResponse<BizLogListVo>(request, records, rows);
		return jqGridList;
	}
	
	@Override
	public void addBizLog(String operKey) {
		addBizLog(operKey, null);
	}
	
	@Override
	public void addBizLog(String operKey, String operDescription) {
		HttpServletRequest request = WebUtil.getCurrentHttpServletRequest();
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(Mp3Constant.MP3_SESSION_USER);
		String userId = null;
		String userName = null;
		String realName = null;
		if (obj != null && obj instanceof SessionUserVo) {
			SessionUserVo sessionUserVo = (SessionUserVo) obj;
			userId = sessionUserVo.getUserId();
			userName = sessionUserVo.getUserName();
			realName = sessionUserVo.getRealName();
		}
		BizLog bizLog = new BizLog();
		bizLog.setId(uuid());
		bizLog.setUserId(userId);
		bizLog.setUserName(userName);
		bizLog.setRealName(realName);
		bizLog.setOperKey(operKey);
		bizLog.setOperDescription(operDescription);
		bizLog.setIp(WebUtil.getIp());
		bizLog.setCreateTime(now());
		bizLog.setUpdateTime(now());
		bizLog.setStatus(StatusEnum.NORMAL.getCode());
		bizLogMapper.insertSelective(bizLog);
	}

	@Override
	public List<ApiLogListVo> findApiLogTopList(Integer num) {
		addBizLog("查询api日志首页列表");
		checkNotEmpty(num, "数量");
		if (num < 0) {
			fail("数量范围1-10");
		}
		if (num > 10) {
			num = 10;
		}
		List<ApiLogListVo> list = apiLogMapperCustom.findApiLogTopList(num);
		return list;
	}

}
