package com.chanwook.demo.domain.auth.api;

public class InvalidInputException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(Throwable exception) {
		super(exception);
	}

	public InvalidInputException(String message, Throwable exception) {
		super(message, exception);
	}

}
