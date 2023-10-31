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
import com.chanwook.demo.domain.auth.api.service.SignupService;
import com.chanwook.demo.domain.auth.api.service.UserSignupCommand;
import com.chanwook.demo.domain.auth.infra.SmtpPort;
import com.chanwook.demo.domain.auth.infra.UserSignupCommandPort;
import com.chanwook.demo.domain.auth.infra.UserSignupException;

@ExtendWith(MockitoExtension.class)
public class SignupUsecaseTest {

	@InjectMocks
	SignupService signupService;

	@Mock
	UserSignupCommandPort signupCommandPort;

	@Mock
	SmtpPort smtpPort;

	@ParameterizedTest
	@MethodSource("commandProvider")
	@DisplayName("회원사입 시 필수값 검증 테스트")
	void checkRequired(UserSignupCommand command) {
		assertThrowsExactly(InvalidInputException.class, () -> signupService.checkRequired(command), "");
	}

	public static Stream<Arguments> commandProvider() {
		return Stream.of(
				// 이메일 null일 때
				arguments(UserSignupCommand.builder()
                        .email(null).password("1234qwer")
                        .passwordVerify("1234qwer")
                        .build()),
				// 비밀번호가 null일 때
                arguments(UserSignupCommand.builder()
                        .email("user@user.dev").password(null)
                        .passwordVerify("1234qwer")
                        .build()),
                // 비밀번호(확인)가 null일 때
                arguments(UserSignupCommand.builder()
                        .email("user@user.dev").password("1234qwer")
                        .passwordVerify(null)
                        .build()));
	}

	@Test
	@DisplayName("회원가입 시 이메일 유효성 검증 테스트")
	public void invaildEmail() {
		UserSignupCommand command = UserSignupCommand.builder()
				.email("useruser.dev")
				.password("1234qwer")
				.passwordVerify("1234qwer")
				.build();
		assertThrowsExactly(InvalidInputException.class, () -> signupService.requestSignup(command), "");
		verify(signupCommandPort, times(0)).addUser(any());
		verify(smtpPort, times(0)).send(any());
	}

	@Test
	@DisplayName("회원가입 시 비밀번호 유효성 검증 테스트")
	public void invaildPassword() {
		UserSignupCommand command = UserSignupCommand.builder()
				.email("user@user.dev")
				.password("qwerasdf")
				.passwordVerify("qwerasdf")
				.build();
		assertThrowsExactly(InvalidInputException.class, () -> signupService.requestSignup(command), "");
		verify(signupCommandPort, times(0)).addUser(any());
		verify(smtpPort, times(0)).send(any());
	}

	@Test
	@DisplayName("회원가입 시 비밀번호 불일치 검증 테스트")
	public void invaildPasswordVerify() {
		UserSignupCommand command = UserSignupCommand.builder()
				.email("user@user.dev")
				.password("qwer1234")
				.passwordVerify("asdf1234")
				.build();
		assertThrowsExactly(InvalidInputException.class, () -> signupService.requestSignup(command), "");
		verify(signupCommandPort, times(0)).addUser(any());
		verify(smtpPort, times(0)).send(any());
	}

	@Test
	@DisplayName("회원가입 실패 테스트")
	public void failAddUser() {
		UserSignupCommand command = UserSignupCommand.builder()
				.email("user@user.dev")
				.password("qwer1234")
				.passwordVerify("qwer1234")
				.build();
		assertThrowsExactly(UserSignupException.class, () -> signupService.requestSignup(command), "");
		verify(signupCommandPort, times(1)).addUser(any());
		verify(smtpPort, times(0)).send(any());
	}

	@Test
	@DisplayName("회원가입 시 이메일 전송 테스트")
	public void sendMail() {
		UserSignupCommand command = UserSignupCommand.builder()
				.email("user@user.dev")
				.password("qwer1234")
				.passwordVerify("qwer1234")
				.build();

		when(signupCommandPort.addUser(any())).thenReturn(Optional.of(User.builder().build()));

		signupService.requestSignup(command);

		verify(signupCommandPort, times(1)).addUser(any());
		verify(smtpPort, times(1)).send(any());
	}
}
