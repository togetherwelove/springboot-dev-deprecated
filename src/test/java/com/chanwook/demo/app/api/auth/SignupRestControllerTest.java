package com.chanwook.demo.app.api.auth;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.chanwook.demo.app.api.auth.dto.SignupRequest;
import com.chanwook.demo.app.api.config.service.JwtService;
import com.chanwook.demo.app.infra.auth.repository.TokenRepository;
import com.chanwook.demo.domain.auth.api.UserSignupUsecase;
import com.chanwook.demo.domain.auth.infra.UserSignupCommandPort;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = SignupRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SignupRestControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	UserSignupUsecase signupService;

	@MockBean
	UserSignupCommandPort userSignupCommandPort;

	@MockBean
	JwtService jwtService;

	@MockBean
	TokenRepository tokenRepository;

	private SignupRequest dto;

	@BeforeEach
	void initDto() {
		dto = new SignupRequest(
				"dean",
				"user@user.dev",
				"1234qwer",
				"1234qwer"
				);
	}

	@Test
	@DisplayName("입력 필수값 체크 (성공)")
	public void check() throws Exception {
		mockMvc.perform(
				post("/auth/signup/check")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[?(@.message == 'success')]").exists());

		verify(signupService, times(1)).checkRequired(any());
	}

	@Test
	@DisplayName("회원가입 요청 (성공)")
	public void request() throws Exception {
		mockMvc.perform(
				post("/auth/signup/request")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[?(@.message == 'success')]").exists());

		verify(signupService, times(1)).requestSignup(any());
	}

	@Test
	@DisplayName("이메일 재전송 요청 (성공)")
	public void resend() throws Exception {
		mockMvc.perform(
				post("/auth/signup/resend")
					.contentType(MediaType.APPLICATION_JSON)
					.content("user@user.dev"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[?(@.message == 'success')]").exists());

		verify(signupService, times(1)).resendMail(any());
	}

	@Test
	@DisplayName("이메일 재전송 요청 (실패)")
	public void resendMailTest_fail() throws Exception {

		mockMvc.perform(
				post("/auth/signup/resend")
					.contentType(MediaType.APPLICATION_JSON)
					.content(""))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code", equalTo("ERROR")))
				.andExpect(jsonPath("$.message", startsWith("Required")));

		verify(signupService, times(0)).resendMail(any());
	}

}
