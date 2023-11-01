package com.chanwook.demo.domain.auth.api.service;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.chanwook.demo.domain.auth.User;
import com.chanwook.demo.domain.auth.api.InvalidInputException;
import com.chanwook.demo.domain.auth.api.SignupUsecase;
import com.chanwook.demo.domain.auth.infra.SmtpPort;
import com.chanwook.demo.domain.auth.infra.UserSignupCommandPort;
import com.chanwook.demo.domain.auth.infra.UserSignupException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService implements SignupUsecase {

	private final UserSignupCommandPort signupCommandPort;
	private final SmtpPort smtpPort;

	@Override
	public void checkRequired(UserSignupCommand signup) {
		if (ObjectUtils.isEmpty(signup)
				|| !StringUtils.hasText(signup.getEmail())
				|| !StringUtils.hasText(signup.getPassword())
				|| !StringUtils.hasText(signup.getPasswordVerify()))
			throw new InvalidInputException("입력값이 없습니다.");
	}
	
	@Override
	public void checkDuplicated(UserSignupCommand signup) {
		Optional<User> user = signupCommandPort.findByEmail(signup.getEmail());
		if (user.isPresent())
			throw new InvalidInputException("이미 존재하는 이메일입니다.");
	}

	@Override
	public void requestSignup(UserSignupCommand signup) {
		verifyRequest(signup);
		Optional<User> user = signupCommandPort.addUser(userMapper.apply(signup));
		if (!user.isPresent())
			throw new UserSignupException("회원 등록에 실패하였습니다.");
		smtpPort.send(user.get().getEmail());
	}

	private void verifyRequest(UserSignupCommand signup) {
		final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		final String PASSWORD_REGEX = "^(?=.*\\d)[\\w\\d!@#$%^&*]{8,}$";

		if (!Pattern.compile(EMAIL_REGEX).matcher(signup.getEmail()).matches())
			throw new InvalidInputException("유효한 이메일 형식이 아닙니다.");
		else if (!Pattern.compile(PASSWORD_REGEX).matcher(signup.getPassword()).matches())
			throw new InvalidInputException("최소 8자 이상이며, 1개의 숫자를 포함하고, 특수 문자를 포함할 수 있습니다.");
		else if (!signup.getPasswordVerify().equals(signup.getPassword()))
			throw new InvalidInputException("비밀번호가 일치하지 않습니다.");

	}

	@Override
	public void resendMail(String email) {
		smtpPort.send(email);
	}

	Function<UserSignupCommand, User> userMapper = req -> User.builder()
			.name(req.getName())
			.email(req.getEmail())
			.password(req.getPassword())
			.build();

}
