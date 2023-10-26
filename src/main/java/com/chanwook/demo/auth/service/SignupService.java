package com.chanwook.demo.auth.service;

import java.util.regex.Pattern;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.chanwook.demo.auth.api.vo.SignupVO;
import com.chanwook.demo.auth.service.usecase.SignupUsecase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService implements SignupUsecase {

	@Override
	public void checkRequired(SignupVO signup) {
		if (ObjectUtils.isEmpty(signup) ||
				!StringUtils.hasText(signup.getEmail()) ||
				!StringUtils.hasText(signup.getPassword()) ||
				!StringUtils.hasText(signup.getPasswordVerify())) {
			throw new RuntimeException("입력값이 없습니다.");
		}
	}

	@Override
	public void requestSignup(SignupVO signup) {
		verifyRequest(signup);
		// TODO 회원가입 요청 후 DB에 저장..
	}

	private void verifyRequest(SignupVO signup) {
		final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
		
		if (!Pattern.compile(EMAIL_REGEX).matcher(signup.getEmail()).matches()) {
			throw new RuntimeException("이메일 형식에 맞지 않습니다.");
		} else if (!Pattern.compile(PASSWORD_REGEX).matcher(signup.getPassword()).matches()) {			
			throw new RuntimeException("적어도 8자 이상이며, 최소 1개의 대문자, 1개의 소문자, 1개의 숫자를 포함해야 하고, 특수 문자를 포함할 수 있습니다.");
		} else if (signup.getPasswordVerify().equals(signup.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");			
		}
	}

}
