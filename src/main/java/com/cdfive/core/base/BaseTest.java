package com.cdfive.core.base;

import com.cdfive.core.util.JsonUtil;
import com.cdfive.core.util.WebUtil;
import com.cdfive.mp3.constant.Mp3Constant;
import com.cdfive.mp3.vo.session.SessionUserVo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
//import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseTest {
	
	protected static ApplicationContext ac;

	static {
		long start = System.currentTimeMillis();
		String[] configLocations = new String[] {
			"classpath:spring/applicationContext-dao.xml",
			"classpath:spring/applicationContext-service.xml",
			"classpath:spring/applicationContext-transation.xml"
		};
		ac = new FileSystemXmlApplicationContext(configLocations);
		log("spring加载耗时:" + (System.currentTimeMillis() - start) / 1000.0 + "s");
	}
	
	protected static void init() {
//		HttpServletRequest request = new MockHttpServletRequest();
		HttpServletRequest request = WebUtil.getCurrentHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		HttpSession httpSession = request.getSession();
		SessionUserVo sessionUserVo = new SessionUserVo();
//		userSessionVo.setUserId("test");
		sessionUserVo.setUserId("b3749b1c-175a-11e7-be4d-003067e0a3f2");
		httpSession.setAttribute(Mp3Constant.MP3_SESSION_USER, sessionUserVo);
	}
	
	public static void log(String obj) {
		System.out.println(obj);
	}
	
	public static void log(Object obj) {
		System.out.println(obj.toString());
	}
	
	public static void logJson(Object obj) {
        log(JsonUtil.toJson(obj));
    }
}
