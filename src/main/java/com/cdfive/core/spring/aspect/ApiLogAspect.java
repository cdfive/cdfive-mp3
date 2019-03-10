package com.cdfive.core.spring.aspect;

import com.cdfive.core.base.BaseResponse;
import com.cdfive.core.constant.ResponseConstant;
import com.cdfive.core.constant.enums.StatusEnum;
import com.cdfive.core.exception.ServiceException;
import com.cdfive.core.util.ClassUtil;
import com.cdfive.core.util.CommonUtil;
import com.cdfive.core.util.JsonUtil;
import com.cdfive.core.util.StreamUtil;
import com.cdfive.core.util.WebUtil;
import com.cdfive.mp3.mapper.ApiLogMapper;
import com.cdfive.mp3.po.ApiLog;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 接口调用日志记录,AOP实现
 */
@Aspect
public class ApiLogAspect {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ApiLogMapper apiLogMapper;

	// @Autowired
	// private AppSysIniService appSysIniService;

	@Around("execution(* com.cdfive.mp3.controller.*.*(..))")
	public Object around(ProceedingJoinPoint point) {
		Object methodResult = null;

		HttpServletRequest request = WebUtil.getCurrentHttpServletRequest();
		String uri = request.getRequestURI();
		String sessionId = request.getSession().getId();
		String ip = WebUtil.getIp(request);

		String url = request.getRequestURL().toString();
		String requestBody = null;
		try {
			requestBody = StreamUtil.getStringFromStream(request.getInputStream());
		} catch (IOException e) {
			log.error("获取请求体失败", e);
		}

		String controllerType = point.getTarget().getClass().getSimpleName();
		MethodSignature joinPointObject = (MethodSignature) point.getSignature();
		Method method = joinPointObject.getMethod();

		String methodName = method.getName();
		String methodRemark = null;

		ApiRemark apiRemark = method.getAnnotation(ApiRemark.class);
		if (apiRemark != null) {
			methodRemark = apiRemark.value();
		}

		String requestType = null;
		String requestValue = null;
		Class<?>[] paramTypes = method.getParameterTypes();
		Object[] paramValues = point.getArgs();
		String[] paramNames = ClassUtil.getParamterNames(point.getTarget().getClass(), point
				.getTarget().getClass().getName(), methodName);

		String appLogId = UUID.randomUUID().toString();
		if (paramTypes != null && paramTypes.length > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < paramNames.length; i++) {
				Class<?> paramType = paramTypes[i];
				if (paramType.equals(HttpServletRequest.class)) {
					continue;
				}
				String paramName = paramNames[i];
				Object paramValue = paramValues[i];
				map.put(paramName, paramValue);
			}
			requestValue = JsonUtil.toJson(map);
		}

		ApiLog apiLog = new ApiLog();
		apiLog.setId(appLogId);
		apiLog.setSessionId(sessionId);
		apiLog.setIp(ip);
		apiLog.setUri(uri);
		apiLog.setUrl(url);
		apiLog.setRequestBody(requestBody);

		apiLog.setControllerType(controllerType);
		apiLog.setMethodName(methodName);
		apiLog.setMethodRemark(methodRemark);

		apiLog.setRequestType(requestType);
		apiLog.setRequestValue(requestValue);

		String code = null;
		String msg = null;
		String responseType = null;
		String responseValue = null;

		int result = ApiResultEnum.SUCC.getCode();

		Date startTime = new Date();
		Date endTime = null;
		apiLog.setStartTime(startTime);

		Object object = null;
		try {
			object = point.proceed();// 实际接口方法调用
			endTime = new Date();
			apiLog.setEndTime(endTime);
			if (object != null) {
				if (object instanceof BaseResponse<?>) {
					BaseResponse<?> BaseResponse = (com.cdfive.core.base.BaseResponse<?>) object;
					code = BaseResponse.getCode();
					msg = BaseResponse.getMsg();
					apiLog.setCode(code);
					apiLog.setMsg(msg);
					if (!ResponseConstant.SUCC.equals(code)) {
						result = ApiResultEnum.RETURN_FAIL.getCode();
					} else {
						result = ApiResultEnum.SUCC.getCode();
					}
					Object data = BaseResponse.getData();
					if (data != null) {
						responseType = data.getClass().getSimpleName();
						responseValue = JsonUtil.toJson(data);
					}
				} else {
					responseType = object.getClass().getSimpleName();
					responseValue = JsonUtil.toJson(object);
				}
				apiLog.setResponseType(responseType);
				apiLog.setResponseValue(CommonUtil.maxString(responseValue, 1024));
			}
			methodResult = object;
			// LOG.info(appLog);
		} catch (Throwable e) {
			endTime = new Date();
			apiLog.setEndTime(endTime);

			apiLog.setExceptionType(e.getClass().getSimpleName());
			apiLog.setExceptionMsg(e != null ? e.getMessage() : null);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			apiLog.setExceptionStackTrace(CommonUtil.maxString(sw.toString(), 1024));
			if (e instanceof ServiceException) {
				result = ApiResultEnum.BUSSINESS_FAIL.getCode();
				log.error("业务异常=>" + e.getMessage(), e);
				code = ((ServiceException) e).getCode();
				msg = e.getMessage();
				apiLog.setCode(code);
				apiLog.setMsg(e.getMessage());
				methodResult = new BaseResponse<Object>(code, msg);
			} else {
				result = ApiResultEnum.OTHER_FAIL.getCode();
				log.error(this, e);
				methodResult = new BaseResponse<Object>(ResponseConstant.INTERNAL_ERR);
			}
			apiLog.setCode(((BaseResponse<?>) methodResult).getCode());
			apiLog.setMsg(((BaseResponse<?>) methodResult).getMsg());
		}
		
		Integer timeCost = (int) (endTime.getTime() - startTime.getTime());
		apiLog.setTimeCost(timeCost);
		apiLog.setResult(result);
		apiLog.setCreateTime(new Date());
		apiLog.setUpdateTime(new Date());
		apiLog.setStatus(StatusEnum.NORMAL.getCode());
		apiLogMapper.insertSelective(apiLog);
		log.info(ToStringBuilder.reflectionToString(apiLog));

		return methodResult;
	}

}
