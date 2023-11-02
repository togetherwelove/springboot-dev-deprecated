package com.chanwook.demo.app.interaction.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chanwook.demo.app.api.auth.dto.SignupRequest;
import com.chanwook.demo.app.api.config.DefaultResponseBody;
import com.chanwook.demo.app.api.config.common.ResponseCodeEnum;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserSingupStoryTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	public void userSignupBasicStory() {
		SignupRequest dto = new SignupRequest("dean", "user@user.dev", "qwer1234", "qwer1234");

		// 입력 필수값 체크
		ResponseEntity<DefaultResponseBody> commandRequiredInputResponse = restTemplate
				.postForEntity("/auth/signup/check", dto, DefaultResponseBody.class);

		assertEquals(HttpStatus.OK, commandRequiredInputResponse.getStatusCode());
		
		Optional<DefaultResponseBody> requiredInputResponseBody = Optional.of(commandRequiredInputResponse.getBody());
		
		requiredInputResponseBody.ifPresent(body -> assertEquals(ResponseCodeEnum.SUCCESS, body.getCode()));		
		
		// 회원가입 요청
		ResponseEntity<DefaultResponseBody> commandRequestSignupResponse = restTemplate
				.postForEntity("/auth/signup/reqeust", dto, DefaultResponseBody.class);

		assertEquals(HttpStatus.OK, commandRequestSignupResponse.getStatusCode());
		
		Optional<DefaultResponseBody> requestSignupResponseBody = Optional.of(commandRequestSignupResponse.getBody());
		
		requestSignupResponseBody.ifPresent(body -> assertEquals(ResponseCodeEnum.SUCCESS, body.getCode()));
		
	}
}
