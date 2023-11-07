package com.chanwook.demo.app.interaction.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chanwook.demo.application.api.config.DefaultResponseBody;
import com.chanwook.demo.application.api.config.common.ResponseCodeEnum;
import com.chanwook.demo.domain.auth.api.service.UserSignupCommand;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserSignupStoryTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	public void userSignupStory() {
		UserSignupCommand command = UserSignupCommand.builder()
				.name("dean")
				.email("user@user.dev")
				.password("1234qwer")
				.passwordVerify("1234qwer")
				.build();

		// 입력 필수값 검사
		ResponseEntity<DefaultResponseBody> requiredInputResponse = restTemplate.postForEntity(
				"/auth/signup/check", command, DefaultResponseBody.class);

		assertEquals(HttpStatus.OK, requiredInputResponse.getStatusCode());

		Optional<DefaultResponseBody> requiredInputResponseBody = Optional.ofNullable(requiredInputResponse.getBody());

		assertEquals(ResponseCodeEnum.SUCCESS, requiredInputResponseBody.map(DefaultResponseBody::getCode).get());

		// 회원가입 요청
		ResponseEntity<DefaultResponseBody> requestSignupResponse = restTemplate.postForEntity(
				"/auth/signup/request", command, DefaultResponseBody.class);

		assertEquals(HttpStatus.OK, requestSignupResponse.getStatusCode());

		Optional<DefaultResponseBody> requestSignupResponseBody = Optional.ofNullable(requestSignupResponse.getBody());

		assertEquals(ResponseCodeEnum.SUCCESS, requestSignupResponseBody.map(DefaultResponseBody::getCode).get());

		// TODO 회원 삭제
	}
}
