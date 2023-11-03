package com.chanwook.demo.domain.auth.infra;

public class UserSignupCommandException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserSignupCommandException(String message) {
		super(message);
	}
	public UserSignupCommandException(Throwable exception) {
		super(exception);
	}
	public UserSignupCommandException(String message, Throwable exception) {
		super(message, exception);
	}

}
