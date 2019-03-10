package com.cdfive.core.exception;

public class ControllerException extends ServiceException {

	public ControllerException() {
		super();
	}

	public ControllerException(String code, String message) {
		super(code, message);
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}

}
