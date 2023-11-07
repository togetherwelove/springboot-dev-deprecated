package com.chanwook.demo.application.api.auth;

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

import com.chanwook.demo.application.api.auth.dto.AuthRequest;
import com.chanwook.demo.application.api.auth.dto.AuthResponse;
import com.chanwook.demo.application.api.auth.service.AuthService;
import com.chanwook.demo.application.api.auth.service.vo.AuthVO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
	private final AuthService tokenService;

	// authentication : 로그인, 인증
	// authority : 회원가입, 인가
	@Operation(summary = "로그인", description = "로그인 후 토큰 발급", tags = {"로그인"})
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest login) {

		AuthVO authResponse = tokenService.authenticate(login);

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

	@Operation(summary = "토큰 재발급", description = "엑세스 토큰 만료 시 재발급", tags = {"로그인"})
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) throws IOException {
		String accessToken = "";
		Optional<String> refreshedAccessToken = tokenService.refreshToken(refreshToken, response);
		if (refreshedAccessToken.isPresent()) accessToken = refreshedAccessToken.get();
		return ResponseEntity.ok().body(new AuthResponse(accessToken));
	}

	// logout은 SecurityConfig에 "auth/logout/"으로 설정되어있음
}
