package com.project.exception;

public class TestException extends RuntimeException {
	private ErrorCode errorCode;

//	public TestException() {
//		super();
//	}
//
//	public TestException(String message) {
//		super(message);
//	}

	public TestException(String message, ErrorCode errorCode) {
	
		super(message);
		this.errorCode = errorCode;
		
	}
//
//	public TestException(String message, Throwable cause) {
//		super(message, cause);
//	}
//
//	public TestException(Throwable cause) {
//		super(cause);
//	}
//
//	protected TestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//	}

}
