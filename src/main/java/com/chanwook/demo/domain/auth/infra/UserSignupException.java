package com.chanwook.demo.domain.auth.infra;

public class UserSignupException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserSignupException(String message) {
		super(message);
	}
	public UserSignupException(Throwable exception) {
		super(exception);
	}
	public UserSignupException(String message, Throwable exception) {
		super(message, exception);
	}

}
