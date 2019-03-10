package com.cdfive.core.filter;

import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.util.CommonUtil;
import com.cdfive.core.util.WebUtil;
import com.cdfive.mp3.po.RequestLog;
import com.cdfive.mp3.service.LogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

public class RequestLogFilter extends OncePerRequestFilter {

	private Log log = LogFactory.getLog(getClass());
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//		System.out.println("==========================");
//		System.out.println(request.getRequestURI());
		
		RequestLog requestLog = new RequestLog();
		requestLog.setId(CommonUtil.uuid());
		requestLog.setSessionId(request.getSession().getId());
		requestLog.setIp(WebUtil.getIp(request));
		requestLog.setUri(request.getRequestURI());
		requestLog.setUrl(request.getRequestURL().toString());
		requestLog.setProtocal(request.getProtocol());
		requestLog.setScheme(request.getScheme());
		requestLog.setServerName(request.getServerName());
		requestLog.setServerPort(request.getServerPort());
		requestLog.setContentType(request.getContentType());
		requestLog.setContentLength(request.getContextPath());
		
		
		StringBuffer sbHeader = new StringBuffer();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			if (sbHeader.length() > 0) {
				sbHeader.append("&");
			}
			sbHeader.append(headerName + "=" + request.getHeader(headerName) + "\r\n");
		}
		requestLog.setHeader(sbHeader.toString());
		
		StringBuffer sbCookie = new StringBuffer();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (sbCookie.length() > 0) {
					sbCookie.append("&");
				}
				sbCookie.append(cookie.getName() + "=" + cookie.getValue() + "\r\n");
			}
		}
		requestLog.setCookie(sbCookie.toString());
		
		requestLog.setQueryString(request.getQueryString());
		
		StringBuffer sbParameter = new StringBuffer();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			if (sbParameter.length() > 0) {
				sbParameter.append("&");
			}
			String parameterName = parameterNames.nextElement();
			sbParameter.append(parameterName + "=" + request.getParameter(parameterName) + "\r\n");
		}
		requestLog.setParameter(sbParameter.toString());
		
		
		
//		System.out.println("[before]");
//		Enumeration<String> enumeration = request.getAttributeNames();
//		while (enumeration.hasMoreElements()) {
//			String name = enumeration.nextElement();
//			Object value = request.getAttribute(name);
//			System.out.println(name + "=" + value);
//		}
		
		requestLog.setStartTime(new Date());
		
		try {
			filterChain.doFilter(request, response);
		} catch (Throwable t) {
			log.error("请求异常", t);
			requestLog.setException(t.getMessage());
		}
		
		requestLog.setResponseContentType(response.getContentType());
//		requestLog.setResponseStatus(response.getStatus());
		
		requestLog.setEndTime(new Date());
		requestLog.setCreateTime(new Date());
		requestLog.setUpdateTime(new Date());
		requestLog.setStatus(StatusEnum.NORMAL.getCode());
		
//		System.out.println("[after]");
//		enumeration = request.getAttributeNames();
//		while (enumeration.hasMoreElements()) {
//			String name = enumeration.nextElement();
//			Object value = request.getAttribute(name);
//			System.out.println(name + "=" + value);
//		}
//		System.out.println(response.getStatus());
		
//		Collection<String> headerNames = response.getHeaderNames();
//		for (String headerName : headerNames) {
//			System.out.println(headerName + "=" + response.getHeader(headerName));
//		}
		
//		System.out.println("==========================");
		
//		log.info(ToStringBuilder.reflectionToString(requestLog));
		
//		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		LogService logService = ac.getBean(LogService.class);
		logService.addRequestLog(requestLog);
	}
	
}
