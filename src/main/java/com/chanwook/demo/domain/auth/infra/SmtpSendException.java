package com.chanwook.demo.domain.auth.infra;

public class SmtpSendException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public SmtpSendException(String message) {
        super(message);
    }

    public SmtpSendException(Throwable exception) {
        super(exception);
    }

    public SmtpSendException(String message, Throwable exception) {
        super(message, exception);
    }

}
