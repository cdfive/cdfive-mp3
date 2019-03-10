package com.cdfive.core.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseController {
	protected Log log = LogFactory.getLog(getClass());
	
	protected <T> BaseResponse<T> succ() {
		return BaseResponse.succ();
	}

	protected <T> BaseResponse<T> succ(T data) {
		return BaseResponse.succ(data);
	}
	
	public void fail(String msg) {
		failMsg(msg);
	}
	
	public void fail(String code, String msg) {
		throw BaseResponse.failServiceException(code, msg);
	}

	public void failMsg(String msg) {
		throw BaseResponse.failServiceExceptionMsg(msg);
	}

	public void failCode(String code) {
		throw BaseResponse.failServiceExceptionCode(code);
	}
}
