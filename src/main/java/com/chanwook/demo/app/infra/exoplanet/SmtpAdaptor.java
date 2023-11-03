package com.chanwook.demo.app.infra.exoplanet;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.chanwook.demo.domain.auth.infra.SmtpPort;
import com.chanwook.demo.domain.auth.infra.SmtpException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SmtpAdaptor implements SmtpPort{
	
	private final JavaMailSender javaMailSender;

	@Override
	public void send(String email, String code) throws SmtpException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'send'");
	}
}
