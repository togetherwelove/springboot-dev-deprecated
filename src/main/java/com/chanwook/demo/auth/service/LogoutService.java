package com.chanwook.demo.auth.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chanwook.demo.auth.Token;
import com.chanwook.demo.auth.repository.TokenRepository;
import com.chanwook.demo.config.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

	private final TokenRepository tokenRepository;
	private final JwtService jwtService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		final String token;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		token = authHeader.substring(7);
		if (StringUtils.hasText(token)) {
			final String username = jwtService.extractUsername(token);
			revokeAllUserTokens(username);
		}
	}

	private void revokeAllUserTokens(String username) {
		List<Token> validTokens = tokenRepository.findAllValidTokenByUsername(username);
		if (!validTokens.isEmpty()) {
			validTokens.forEach(t -> {
				t.setExpired(true);
				t.setRevoked(true);
				tokenRepository.save(t);
			});
		}
	}
}
