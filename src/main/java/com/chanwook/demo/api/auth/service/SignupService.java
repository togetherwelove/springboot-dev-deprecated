package com.chanwook.demo.api.auth.service;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.chanwook.demo.api.auth.dto.User;
import com.chanwook.demo.api.auth.infra.SignupPort;
import com.chanwook.demo.api.auth.service.usecase.SignupUsecase;
import com.chanwook.demo.api.auth.service.vo.SignupRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService implements SignupUsecase {

	private final SignupPort signupPort;

	@Override
	public void checkRequired(SignupRequest signup) {
		if (ObjectUtils.isEmpty(signup) ||
				!StringUtils.hasText(signup.getEmail()) ||
				!StringUtils.hasText(signup.getPassword()) ||
				!StringUtils.hasText(signup.getPasswordVerify())) {
			throw new RuntimeException("입력값이 없습니다.");
		}
	}

	@Override
	public void requestSignup(SignupRequest signup) {
		verifyRequest(signup);
		Optional<User> user = signupPort.addUser(userMapper.apply(signup));
		if( !user.isPresent()) {
			throw new RuntimeException("회원 등록에 실패하였습니다.");
		}
	}

	private void verifyRequest(SignupRequest signup) {
		final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

		if (!Pattern.compile(EMAIL_REGEX).matcher(signup.getEmail()).matches()) {
			throw new RuntimeException("유효한 이메일 형식이 아닙니다.");
		} else if (!Pattern.compile(PASSWORD_REGEX).matcher(signup.getPassword()).matches()) {
			throw new RuntimeException("적어도 8자 이상이며, 최소 1개의 대문자, 1개의 소문자, 1개의 숫자를 포함해야 하고, 특수 문자를 포함할 수 있습니다.");
		} else if (signup.getPasswordVerify().equals(signup.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
	}

	Function<SignupRequest, User> userMapper = req -> User.builder()
			.name(req.getName())
			.email(req.getEmail())
			.password(req.getPassword())
			.build();

}
