package com.cdfive.core.base;

import com.cdfive.core.util.WebUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查登录filter
 */
public abstract class BaseLoginFilter extends OncePerRequestFilter {

	private Log log = LogFactory.getLog(getClass());

	private String ignorePath;

	private String sessionStatusKey = "session_status";

	private String sessionStatusTimeout = "timeout";

	private String sessionStatusTimeoutMsg = "登录超时，请重新登录";

	private String sessionStatusOtherLogin = "other_login";

	private String sessionStatusOtherLoginMsg = "您的账号已在其它地点登录";

	private String errorMsgKey = "errorMsg";

	private String errorPage = "/WEB-INF/views/error.jsp";

	public void setIgnorePath(String ignorePath) {
		this.ignorePath = ignorePath;
	}

	public void setSessionStatusKey(String sessionStatusKey) {
		this.sessionStatusKey = sessionStatusKey;
	}

	public void setSessionStatusTimeout(String sessionStatusTimeout) {
		this.sessionStatusTimeout = sessionStatusTimeout;
	}

	public void setSessionStatusTimeoutMsg(String sessionStatusTimeoutMsg) {
		this.sessionStatusTimeoutMsg = sessionStatusTimeoutMsg;
	}

	public void setSessionStatusOtherLogin(String sessionStatusOtherLogin) {
		this.sessionStatusOtherLogin = sessionStatusOtherLogin;
	}

	public void setSessionStatusOtherLoginMsg(String sessionStatusOtherLoginMsg) {
		this.sessionStatusOtherLoginMsg = sessionStatusOtherLoginMsg;
	}

	public void setErrorMsgKey(String errorMsgKey) {
		this.errorMsgKey = errorMsgKey;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.equals("/")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (ignorePath != null) {
			String[] ignorePaths = ignorePath.split(",");
			for (String tmpPath : ignorePaths) {
				if (uri.startsWith(tmpPath.trim())) {
					filterChain.doFilter(request, response);
					return;
				}
			}
		}

		String sessionStatusValue = null;
		String sessionStatusMsg = null;
		boolean pass = checkLogin(request);

		if (!pass) {
			sessionStatusValue = sessionStatusTimeout;
			sessionStatusMsg = sessionStatusTimeoutMsg;
		} else {
			pass = checkOtherLogin(request);
			if (!pass) {
				sessionStatusValue = sessionStatusOtherLogin;
				sessionStatusMsg = sessionStatusOtherLoginMsg;
			}
		}

		if (!pass) {
			if (WebUtil.isAjaxRequest(request)) {
				response.setHeader(sessionStatusKey, sessionStatusValue);
			} else {
				request.setAttribute(errorMsgKey, sessionStatusMsg);
				request.getRequestDispatcher(errorPage).forward(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	/**
	 * 检查是否登录超时，由子类实现
	 */
	protected boolean checkLogin(HttpServletRequest request) {
		return true;
	}

	/**
	 * 检查是否其它地点登录，由子类实现
	 */
	protected boolean checkOtherLogin(HttpServletRequest request) {
		return true;
	}

}
