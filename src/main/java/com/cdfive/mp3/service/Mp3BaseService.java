package com.cdfive.mp3.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.cdfive.core.base.BaseService;
import com.cdfive.core.util.WebUtil;
import com.cdfive.mp3.constant.Mp3Constant;
import com.cdfive.mp3.vo.session.SessionUserVo;

public class Mp3BaseService extends BaseService {
	
	@Autowired
	protected LogService logService;
	
	protected void addBizLog(String operKey) {
		logService.addBizLog(operKey);
	}
	
	protected void addBizLog(String operKey, String operDescription) {
		logService.addBizLog(operKey, operDescription);
	}
	
	protected void setSessionUser(Object obj) {
		HttpServletRequest request = WebUtil.getCurrentHttpServletRequest();
		HttpSession session = request.getSession();
		session.setAttribute(Mp3Constant.MP3_SESSION_USER, obj);
	}

	protected SessionUserVo getSessionUser() {
		HttpServletRequest request = WebUtil.getCurrentHttpServletRequest();
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(Mp3Constant.MP3_SESSION_USER);
		return (SessionUserVo) obj;
	}
	
	protected String getSessionUserId() {
		return getSessionUser() == null ? null : getSessionUser().getUserId();
	}
	
	protected void invalidateSession() {
		HttpServletRequest request = WebUtil.getCurrentHttpServletRequest();
		HttpSession session = request.getSession();
		session.invalidate();
	}
}
