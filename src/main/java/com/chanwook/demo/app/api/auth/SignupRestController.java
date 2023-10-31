package com.chanwook.demo.app.api.auth;

import java.util.function.Function;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.app.api.auth.dto.SignupRequest;
import com.chanwook.demo.domain.auth.api.SignupUsecase;
import com.chanwook.demo.domain.auth.api.service.UserSignupCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/signup")
@RequiredArgsConstructor
public class SignupRestController {

	private final SignupUsecase signupService;

	@PostMapping("/check")
	public void checkRequired(@RequestBody SignupRequest dto) {
		signupService.checkRequired(commandMapper.apply(dto));
	}

	@PostMapping("/request")
	public void requestSignup(@RequestBody SignupRequest dto) {
		signupService.requestSignup(commandMapper.apply(dto));
	}

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
