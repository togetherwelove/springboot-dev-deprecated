package com.chanwook.demo.domain.auth.infra;

public class UserSignupRequsetException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserSignupRequsetException(String message) {
		super(message);
	}
	public UserSignupRequsetException(Throwable exception) {
		super(exception);
	}
	public UserSignupRequsetException(String message, Throwable exception) {
		super(message, exception);
	}

}
