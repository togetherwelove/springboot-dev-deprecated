package com.chanwook.demo.domain.auth.api.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.chanwook.demo.domain.auth.User;
import com.chanwook.demo.domain.auth.api.UserSignupCommandUsecase;
import com.chanwook.demo.domain.auth.infra.SmtpPort;
import com.chanwook.demo.domain.auth.infra.UserSignupCommandException;
import com.chanwook.demo.domain.auth.infra.UserSignupCommandPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSignupCommandService implements UserSignupCommandUsecase {

	private final UserSignupCommandPort userSignupCommandPort;
	private final SmtpPort smtpPort;

	@Override
	public void checkRequired(UserSignupCommand signup) {
		if (ObjectUtils.isEmpty(signup) || !StringUtils.hasText(signup.getEmail())
				|| !StringUtils.hasText(signup.getPassword()) || !StringUtils.hasText(signup.getPasswordVerify()))
			throw new InvalidInputException("입력값이 없습니다.");
	}

	@Override
	@Transactional
	public void requestSignup(UserSignupCommand signup) {
		verifyRequest(signup);
		checkDuplicated(signup);
		Optional<User> addedUser = userSignupCommandPort.addUser(userMapper.apply(signup));
		if (!addedUser.isPresent())
			throw new UserSignupCommandException("회원 등록에 실패하였습니다.");
		smtpPort.send(addedUser.get().getEmail(), generateCode());
	}

	@Override
	public void resendMail(String email) {
		smtpPort.send(email, generateCode());
	}

	private void checkDuplicated(UserSignupCommand signup) {
		Optional<User> user = userSignupCommandPort.findByEmail(userMapper.apply(signup));
		if (user.isPresent()) {
			throw new UserSignupCommandException("이미 등록된 회원 이메일입니다.");
		}
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

	Function<UserSignupCommand, User> userMapper = req -> User.builder().name(req.getName()).email(req.getEmail())
			.password(req.getPassword()).build();

	private String generateCode() {
		int len = 6;
		try {
			Random random = SecureRandom.getInstanceStrong();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < len; i++) {
				builder.append(random.nextInt(10));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
