package com.chanwook.demo.domain.auth.infra;

public interface SmtpPort {
	void send(String email) throws SmtpSendException;
}
