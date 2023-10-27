package com.chanwook.demo.api.auth;

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

import com.chanwook.demo.api.auth.dto.Token;
import com.chanwook.demo.api.auth.service.vo.LoginRequest;
import com.chanwook.demo.entity.service.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRest {
	private final TokenService tokenService;

	// authentication : 로그인, 인증
	// authority : 회원가입, 인가
	@PostMapping("/login")
	public ResponseEntity<Token> authenticate(@RequestBody LoginRequest login) {

		Token authResponse = tokenService.authenticate(login);

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(86400)
				.domain("localhost")
				.build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
				.body(new Token(authResponse.getAccessToken(), null));
	}

	@PostMapping("/refresh")
	public ResponseEntity<Token> refreshToken(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) throws IOException {
		String accessToken = "";
		Optional<String> refreshedAccessToken = tokenService.refreshToken(refreshToken, response);
		if (refreshedAccessToken.isPresent()) {
			accessToken = refreshedAccessToken.get();
		}
		return ResponseEntity.ok().body(new Token(accessToken, null));
	}
	// logout은 SecurityConfig에 "auth/logout/"으로 설정되어있음
}
