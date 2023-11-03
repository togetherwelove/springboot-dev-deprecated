package com.chanwook.demo.domain.auth.infra;

public class SmtpException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public SmtpException(String message) {
        super(message);
    }

    public SmtpException(Throwable exception) {
        super(exception);
    }

    public SmtpException(String message, Throwable exception) {
        super(message, exception);
    }

}
