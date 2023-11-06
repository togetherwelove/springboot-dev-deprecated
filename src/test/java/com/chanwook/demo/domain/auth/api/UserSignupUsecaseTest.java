package com.chanwook.demo.domain.auth.api;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chanwook.demo.domain.auth.User;
import com.chanwook.demo.domain.auth.api.service.InvalidInputException;
import com.chanwook.demo.domain.auth.api.service.UserSignupCommand;
import com.chanwook.demo.domain.auth.api.service.UserSignupService;
import com.chanwook.demo.domain.auth.infra.SmtpPort;
import com.chanwook.demo.domain.auth.infra.UserSignupRequsetException;
import com.chanwook.demo.domain.auth.infra.UserSignupCommandPort;

@ExtendWith(MockitoExtension.class)
public class UserSignupUsecaseTest {

	@InjectMocks
	UserSignupService userSignupService;

	@Mock
	UserSignupCommandPort userSignupCommandPort;

	@Mock
	SmtpPort smtpPort;

	@ParameterizedTest
	@MethodSource("commandProvider")
	@DisplayName("필수값이 null일 때")
	void checkRequired(UserSignupCommand command) {
		assertThrowsExactly(InvalidInputException.class, () ->
		userSignupService.checkRequired(command), "");
	}

	public static Stream<Arguments> commandProvider() {
		return Stream.of(
				// 이메일 null일 때
				arguments(UserSignupCommand.builder()
                        .email(null)
                        .password("1234qwer")
                        .passwordVerify("1234qwer")
                        .build()),
				// 비밀번호가 null일 때
                arguments(UserSignupCommand.builder()
                        .email("user@user.dev")
                        .password(null)
                        .passwordVerify("1234qwer")
                        .build()),
                // 비밀번호(확인)가 null일 때
                arguments(UserSignupCommand.builder()
                        .email("user@user.dev")
                        .password("1234qwer")
                        .passwordVerify(null)
                        .build()));
	}

	@Test
	@DisplayName("이메일이 유효하지 않을 때")
	public void invaildEmail() {

		UserSignupCommand command = UserSignupCommand.builder()
				.email("useruser.dev")
				.password("1234qwer")
				.passwordVerify("1234qwer")
				.build();

		assertThrowsExactly(InvalidInputException.class, () ->
		userSignupService.requestSignup(command), "");

		verify(userSignupCommandPort, times(0)).addUser(any());
		verify(smtpPort, times(0)).send(any());
	}

	@Test
	@DisplayName("비밀번호가 유효하지 않을 때")
	public void invaildPassword() {

		UserSignupCommand command = UserSignupCommand.builder()
				.email("user@user.dev")
				.password("qwerasdf")
				.passwordVerify("qwerasdf")
				.build();

		assertThrowsExactly(InvalidInputException.class, () ->
		userSignupService.requestSignup(command), "");

		verify(userSignupCommandPort, times(0)).addUser(any());
		verify(smtpPort, times(0)).send(any());
	}

	@Test
	@DisplayName("비밀번호가 불일치할 때")
	public void notMatchedPasswordVerify() {

		UserSignupCommand command = UserSignupCommand.builder()
				.email("user@user.dev")
				.password("qwer1234")
				.passwordVerify("asdf1234")
				.build();

		assertThrowsExactly(InvalidInputException.class, () ->
		userSignupService.requestSignup(command), "");

		verify(userSignupCommandPort, times(0)).addUser(any());
		verify(smtpPort, times(0)).send(any());
	}

	@Test
	@DisplayName("회원가입 시도했을 때")
	public void failAddUser() {

		UserSignupCommand command = UserSignupCommand.builder()
				.email("user@user.dev")
				.password("qwer1234")
				.passwordVerify("qwer1234")
				.build();

		assertThrowsExactly(UserSignupRequsetException.class, () ->
		userSignupService.requestSignup(command), "");

		verify(userSignupCommandPort, times(1)).addUser(any());
		verify(smtpPort, times(0)).send(any());
	}

	@Test
	@DisplayName("이메일을 전송했을 때")
	public void sendMail() {
		UserSignupCommand command = UserSignupCommand.builder()
				.email("user@user.dev")
				.password("qwer1234")
				.passwordVerify("qwer1234")
				.build();

		when(userSignupCommandPort.addUser(any())).thenReturn(Optional.of(User.builder().build()));

		userSignupService.requestSignup(command);

		verify(userSignupCommandPort, times(1)).addUser(any());
		verify(smtpPort, times(1)).send(any());
	}
}
