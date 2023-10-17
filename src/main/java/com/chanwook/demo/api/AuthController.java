package com.chanwook.demo.api;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.controller.dto.AuthRequest;
import com.chanwook.demo.controller.dto.AuthResponse;
import com.chanwook.demo.model.auth.User;
import com.chanwook.demo.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	// authentication : 로그인, 인증
	// authority : 회원가입, 인가
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest req) {

		User user = User.builder()
				.email(req.getEmail())
				.password(req.getPassword())
				.build();

		AuthResponse authResponse = authService.authenticate(user);

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(86400)
				.domain("localhost")
				.build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
				.body(new AuthResponse(authResponse.getAccessToken()));

	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(@CookieValue String refreshToken, HttpServletResponse response) throws IOException {
		String newAccessToken = "";
		Optional<String> refreshedAccessToken = authService.refreshToken(refreshToken, response);
		if (refreshedAccessToken.isPresent()) {
			newAccessToken = refreshedAccessToken.get();
		}
		return ResponseEntity.ok().body(new AuthResponse(newAccessToken));
	}

	// logout은 SecurityConfig에 "auth/logout/"으로 설정되어있음
}
