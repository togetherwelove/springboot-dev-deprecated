package com.chanwook.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.model.User;
import com.chanwook.demo.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<User> authenticate(@RequestBody User user) {
		User logedInUser = authService.authenticate(user);
		return ResponseEntity.ok(logedInUser);

		// authentication : 로그인, 인증
		// authority : 회원가입, 인가
	}
}
