package com.cdfive.core.base;

import com.cdfive.core.constant.ResponseConstant;
import com.cdfive.core.exception.ControllerException;
import com.cdfive.core.exception.ServiceException;
import com.cdfive.core.exception.ServiceNotRollbackException;


public class BaseResponse<T> {
	// 响应码
	private String code;
	// 消息
	private String msg;
	// 业务数据
	private T data;
	
	public BaseResponse() {
	}

	public static BaseResponse<String> SUCC = new BaseResponse<String>(ResponseConstant.SUCC);
	
	public static <T> BaseResponse<T> succ() {
		return new BaseResponse<T>(ResponseConstant.SUCC);
	}
	
	public static <T> BaseResponse<T> succ(T data) {
		return new BaseResponse<T>(ResponseConstant.SUCC, data);
	}
	
	public static <T> BaseResponse<T> succMsg(String msg) {
		return new BaseResponse<T>(ResponseConstant.SUCC,msg);
	}
	public static <T> BaseResponse<T> fail() {
		return new BaseResponse<T>(ResponseConstant.BUSSINESS_ERR, "失败");
	}
	
	public static <T> BaseResponse<T> failMsg(String msg) {
		return new BaseResponse<T>(ResponseConstant.BUSSINESS_ERR, msg);
	}
	
	public static <T> BaseResponse<T> failCode(String code) {
		return new BaseResponse<T>(code);
	}
	
	public static <T> ServiceException failServiceExceptionMsg(String msg) {
		BaseResponse<T> appResponse = failMsg(msg);
		return toServiceException(appResponse);
	}
	
	public static <T> ServiceException failServiceExceptionCode(String code) {
		BaseResponse<T> appResponse = failCode(code);
		return toServiceException(appResponse);
	}
	
	public static <T> ServiceException failServiceException(String code, String msg) {
		BaseResponse<T> appResponse = new BaseResponse<T>(code, msg);
		return toServiceException(appResponse);
	}
	
	public static <T> ServiceException failServiceNotRollbackExceptionMsg(String msg) {
		BaseResponse<T> appResponse = failMsg(msg);
		return toServiceNotRollbackException(appResponse);
	}
	
	public static <T> ServiceException failServiceNotRollbackExceptionCode(String code) {
		BaseResponse<T> appResponse = failCode(code);
		return toServiceNotRollbackException(appResponse);
	}
	
	public static <T> ServiceException failServiceNotRollbackException(String code, String msg) {
		BaseResponse<T> appResponse = new BaseResponse<T>(code, msg);
		return toServiceNotRollbackException(appResponse);
	}
	
	public static <T> ControllerException failControllerExceptionMsg(String msg) {
		BaseResponse<T> appResponse = failMsg(msg);
		return toControllerException(appResponse);
	}
	
	public static <T> ControllerException failControllerExceptionCode(String code) {
		BaseResponse<T> appResponse = failCode(code);
		return toControllerException(appResponse);
	}
	
	public static <T> ControllerException failControllerException(String code, String msg) {
		BaseResponse<T> appResponse = new BaseResponse<T>(code, msg);
		return toControllerException(appResponse);
	}
	
	private static <T> ServiceException toServiceException(BaseResponse<T> appResponse) {
		ServiceException ex = new ServiceException(appResponse.getCode(), appResponse.getMsg());
		return ex;
	}
	
	private static <T> ServiceNotRollbackException toServiceNotRollbackException(BaseResponse<T> appResponse) {
		ServiceNotRollbackException ex = new ServiceNotRollbackException(appResponse.getCode(), appResponse.getMsg());
		return ex;
	}
	
	private static <T> ControllerException toControllerException(BaseResponse<T> appResponse) {
		ControllerException ex = new ControllerException(appResponse.getCode(), appResponse.getMsg());
		return ex;
	}
	
	public BaseResponse(String code) {
		this.code = code;
		this.msg = ResponseConstant.getMsgByCode(code);
	}
	
	public BaseResponse(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public BaseResponse(String code, T data) {
		this.code = code;
		this.msg = ResponseConstant.getMsgByCode(code);
		this.data = data;
	}
	
	public BaseResponse(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
