package com.cdfive.core.exception;

public class ServiceNotRollbackException extends ServiceException {

	public ServiceNotRollbackException() {
		super();
	}

	public ServiceNotRollbackException(String code, String message) {
		super(code, message);
	}

	public ServiceNotRollbackException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceNotRollbackException(String message) {
		super(message);
	}

	public ServiceNotRollbackException(Throwable cause) {
		super(cause);
	}
	
}
