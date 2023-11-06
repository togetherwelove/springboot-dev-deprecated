package com.chanwook.demo.domain.auth.api.service;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.chanwook.demo.domain.auth.User;
import com.chanwook.demo.domain.auth.api.UserSignupUsecase;
import com.chanwook.demo.domain.auth.infra.SmtpPort;
import com.chanwook.demo.domain.auth.infra.UserSignupRequsetException;
import com.chanwook.demo.domain.auth.infra.UserSignupCommandPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSignupService implements UserSignupUsecase {

	private final UserSignupCommandPort userSignupCommandPort;
	private final SmtpPort smtpPort;

	@Override
	public void checkRequired(UserSignupCommand command) {
		if (ObjectUtils.isEmpty(command)
				|| !StringUtils.hasText(command.getEmail())
				|| !StringUtils.hasText(command.getPassword())
				|| !StringUtils.hasText(command.getPasswordVerify()))
			throw new InvalidInputException("입력값이 없습니다.");
	}

	@Override
	@Transactional
	public void requestSignup(UserSignupCommand command) {
		verifyRequest(command);
		Optional<User> addedUser = Optional.empty();

		try {
			addedUser = userSignupCommandPort.addUser(userMapper.apply(command));
		} catch (Exception e) {
			throw new UserSignupRequsetException("이미 등록된 메일 주소입니다.");
		}

		if(!addedUser.isPresent())
			throw new UserSignupRequsetException("회원가입에 실패하였습니다.");

		try {
			smtpPort.send(addedUser.get().getEmail());
		} catch (UnsupportedOperationException e) {
			log.warn("메일 기능은 동작하지 않습니다.");
		}
	}

	@Override
	public void resendMail(String email) {
		smtpPort.send(email);
	}

	private void verifyRequest(UserSignupCommand command) {
		final String EMAIL_REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		final String PASSWORD_REGEX = "^(?=.*\\d)[\\w\\d!@#$%^&*]{8,}$";

		if (!Pattern.compile(EMAIL_REGEX).matcher(command.getEmail()).matches())
			throw new InvalidInputException("유효한 이메일 형식이 아닙니다.");
		else if (!Pattern.compile(PASSWORD_REGEX).matcher(command.getPassword()).matches())
			throw new InvalidInputException("비밀번호는 최소 8자 이상이며, 최소 1개의 숫자를 포함하고, 특수 문자를 포함할 수 있습니다.");
		else if (!command.getPasswordVerify().equals(command.getPassword()))
			throw new InvalidInputException("비밀번호가 일치하지 않습니다.");

	}

	Function<UserSignupCommand, User> userMapper = req -> User.builder()
			.name(req.getName())
			.email(req.getEmail())
			.password(req.getPassword())
			.build();
}
