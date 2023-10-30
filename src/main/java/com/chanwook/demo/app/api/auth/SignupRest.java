package com.chanwook.demo.app.api.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.domain.auth.api.SignupUsecase;
import com.chanwook.demo.domain.auth.api.service.UserSignupCommand;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupRest {

	private final SignupUsecase signupService;

	@PostMapping("/check")
	public void checkRequired(@RequestBody UserSignupCommand signup) {
		signupService.checkRequired(signup);
	}

	@PostMapping("/request")
	public void requestSignup(@RequestBody UserSignupCommand signup) {
		signupService.requestSignup(signup);
	}
}
