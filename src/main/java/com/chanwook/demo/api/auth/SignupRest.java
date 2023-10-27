package com.chanwook.demo.api.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.api.auth.service.usecase.SignupUsecase;
import com.chanwook.demo.api.auth.service.vo.SignupRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupRest {

	private final SignupUsecase signupService;

	@PostMapping("/check")
	public void checkRequired(@RequestBody SignupRequest signup) {
		signupService.checkRequired(signup);
	}

	@PostMapping("/request")
	public void requestSignup(@RequestBody SignupRequest signup) {
		signupService.requestSignup(signup);
	}
}
