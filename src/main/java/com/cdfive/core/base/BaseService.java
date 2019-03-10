package com.cdfive.core.base;

import com.cdfive.core.constant.ResponseConstant;
import com.cdfive.core.util.CommonUtil;
import com.cdfive.core.util.DateUtil;
import com.cdfive.core.util.RegexUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Date;

public class BaseService {
	protected final Log log = LogFactory.getLog(getClass());

	protected Date now() {
		return DateUtil.now();
	}

	protected String uuid() {
		return CommonUtil.uuid();
	}

	protected boolean isNotEmpty(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Collection<?>) {
			return CollectionUtils.isNotEmpty((Collection<?>) obj);
		}
		if (obj instanceof String) {
			return StringUtils.isNotEmpty((String) obj);
		}
		return true;
	}
	
	protected boolean isEmpty(Object obj) {
		return !isNotEmpty(obj);
	}

	protected void checkNotEmpty(String s, String key) {
		if (StringUtils.isEmpty(s)) {
			fail(ResponseConstant.PARAM_EMPTY, key + "不能为空");
		}
	}

	protected void checkNotEmpty(Integer s, String key) {
		if (s == null) {
			fail(ResponseConstant.PARAM_EMPTY, key + "不能为空");
		}
	}

	protected void checkNotEmpty(Long s, String key) {
		if (s == null) {
			fail(ResponseConstant.PARAM_EMPTY, key + "不能为空");
		}
	}

	protected void checkNotEmpty(Collection<?> c, String key) {
		if (c == null || c.size() == 0) {
			fail(ResponseConstant.PARAM_EMPTY, key + "不能为空");
		}
	}

	protected void checkExists(Object obj) {
		if (obj == null) {
			failPermissionDeny();
		}
	}

	protected <T> void checkRange(T val, T[] validVals) {
		for (T validVal : validVals) {
			if (validVal.equals(val)) {
				return;
			}
		}
		failCode(ResponseConstant.PARAM_INVALID);
	}

	protected void checkPhone(String phone) {
		checkPhone(phone, null);
	}

	protected void checkPhone(String phone, String key) {
		if (StringUtils.isEmpty(key)) {
			key = "手机号";
		}
		checkNotEmpty(phone, key);
		if (!RegexUtil.checkPhone(phone)) {
			fail(ResponseConstant.PARAM_INVALID, key + "格式错误");
		}
	}

	protected void checkPwd(String str) {
		checkNotEmpty(str, "密码");
		if (!RegexUtil.checkPwd(str)) {
			fail(ResponseConstant.PARAM_INVALID, "密码为字母或数字组合，至少6位");
		}
	}

	protected Integer checkPageNum(Integer pageNum) {
		if (pageNum == null || pageNum <= 0) {
			return 1;
		}
		return pageNum;
	}

	protected Integer checkPageSize(Integer pageSize) {
		if (pageSize == null || pageSize <= 0 || pageSize > 10) {
			return 10;
		}
		return pageSize;
	}

	public void failPermissionDeny() {
		failCode(ResponseConstant.PERMISSION_DENY);
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

	public void failNotRollback(String msg) {
		failNotRollbackMsg(msg);
	}

	public void failNotRollback(String code, String msg) {
		throw BaseResponse.failServiceNotRollbackException(code, msg);
	}

	public void failNotRollbackCode(String code) {
		throw BaseResponse.failServiceNotRollbackExceptionCode(code);
	}

	public void failNotRollbackMsg(String msg) {
		throw BaseResponse.failServiceNotRollbackExceptionMsg(msg);
	}

}
