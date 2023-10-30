package com.chanwook.demo.app.infra.exoplanet;

import org.springframework.stereotype.Component;

import com.chanwook.demo.domain.auth.infra.SmtpPort;
import com.chanwook.demo.domain.auth.infra.SmtpSendException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SmtpAdaptor implements SmtpPort{

	@Override
	public void send(String email) throws SmtpSendException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'send'");
	}

}
