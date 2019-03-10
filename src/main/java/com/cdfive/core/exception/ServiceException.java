package com.cdfive.core.exception;


public class ServiceException extends RuntimeException {

	protected String code;

	public ServiceException() {
		super();
	}

	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String toString() {
	    return "[code=" + getCode() + ", message=" + getMessage() + "]";
	}
}
