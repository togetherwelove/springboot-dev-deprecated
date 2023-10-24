package com.chanwook.demo.auth.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.auth.api.dto.LoginRequest;
import com.chanwook.demo.auth.api.dto.SignupRequest;
import com.chanwook.demo.auth.api.dto.TokenVO;
import com.chanwook.demo.auth.service.SignupService;
import com.chanwook.demo.auth.service.TokenService;
import com.chanwook.demo.user.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final TokenService tokenService;
	private final SignupService signupService;
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}

	// authentication : 로그인, 인증
	// authority : 회원가입, 인가
	@PostMapping("/login")
	public ResponseEntity<TokenVO> authenticate(@RequestBody LoginRequest login) {

		User user = User.builder()
				.email(login.getEmail())
				.password(login.getPassword())
				.build();
		TokenVO authResponse = tokenService.authenticate(user);

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(86400)
				.domain("localhost")
				.build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
				.body(new TokenVO(authResponse.getAccessToken(), null));

	}

	@PostMapping("/signup")
	public ResponseEntity<User> authorize(@RequestBody SignupRequest signup) {
		User user = signupService.signup(signup);
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/refresh")
	public void refreshToken(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) throws IOException {
		tokenService.refreshToken(refreshToken, response);
	}
	// logout은 SecurityConfig에 "auth/logout/"으로 설정되어있음
}
