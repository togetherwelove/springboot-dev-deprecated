package com.chanwook.demo.app.api.auth;

import java.util.function.Function;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.app.api.auth.dto.SignupRequest;
import com.chanwook.demo.domain.auth.api.UserSignupUsecase;
import com.chanwook.demo.domain.auth.api.service.UserSignupCommand;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/signup")
@RequiredArgsConstructor
public class SignupRestController {

	private final UserSignupUsecase signupService;

	@Operation(summary = "회원가입 입력 필수값 체크", description = "이메일, 패스워드, 패스워드(확인)가 입력 됐는지 체크", tags = {"회원가입"})
	@PostMapping("/check")
	public void checkRequired(@RequestBody SignupRequest dto) {
		signupService.checkRequired(commandMapper.apply(dto));
	}

	@Operation(summary = "회원가입 요청", description = "유효성 검사 후 확인 메일 발송", tags = {"회원가입"})
	@PostMapping("/request")
	public void requestSignup(@RequestBody SignupRequest dto) {
		signupService.requestSignup(commandMapper.apply(dto));
	}

	@Operation(summary = "메일 재전송", description = "메일을 못 받았을 경우 재발송", tags = {"회원가입"})
	@PostMapping("/resend")
	public void resend(@RequestBody String email) {
		signupService.resendMail(email);
	}

	Function<SignupRequest, UserSignupCommand> commandMapper = req -> new UserSignupCommand(
			req.getName(),
			req.getEmail(),
			req.getPassword(),
			req.getPasswordVerify());
}

