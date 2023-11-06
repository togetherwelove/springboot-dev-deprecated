package com.chanwook.demo.domain.auth.infra;

public class UserRequsetException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserRequsetException(String message) {
		super(message);
	}
	public UserRequsetException(Throwable exception) {
		super(exception);
	}
	public UserRequsetException(String message, Throwable exception) {
		super(message, exception);
	}

}
