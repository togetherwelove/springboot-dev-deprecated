package com.chanwook.demo.application.infra.exoplanet;

import org.springframework.stereotype.Component;

import com.chanwook.demo.domain.auth.infra.SmtpException;
import com.chanwook.demo.domain.auth.infra.SmtpPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SmtpAdaptor implements SmtpPort {

	@Override
	public void send(String email) throws SmtpException {
		throw new UnsupportedOperationException("Unimplemented method 'send'");
		// TODO 회원 확인 이메일 보내기 기능 구현
	}
}
