package com.cdfive.mp3.filter;

import com.cdfive.core.base.BaseLoginFilter;
import com.cdfive.core.util.WebUtil;
import com.cdfive.mp3.constant.Mp3Constant;
import com.cdfive.mp3.vo.session.SessionUserVo;

import javax.servlet.http.HttpServletRequest;

public class Mp3LoginFilter extends BaseLoginFilter {

	@Override
	protected boolean checkLogin(HttpServletRequest request) {
		Object obj = WebUtil.getCurrentHttpServletRequest().getSession().getAttribute(Mp3Constant.MP3_SESSION_USER);
		if (obj != null && obj instanceof SessionUserVo) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean checkOtherLogin(HttpServletRequest request) {
		return true;
	}
	
}
