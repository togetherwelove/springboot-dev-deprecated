package com.chanwook.demo.auth.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.chanwook.demo.auth.Token;
import com.chanwook.demo.auth.TokenType;
import com.chanwook.demo.auth.api.dto.TokenVO;
import com.chanwook.demo.auth.repository.TokenRepository;
import com.chanwook.demo.config.service.JwtService;
import com.chanwook.demo.user.User;
import com.chanwook.demo.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final JwtService jwtService;

	public TokenVO authenticate(User user) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		String accessToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveToken(user, accessToken);
		saveToken(user, refreshToken);
		return new TokenVO(accessToken, refreshToken);
	}

	private void revokeAllUserTokens(User user) {
		List<Token> validTokens = tokenRepository.findAllValidTokenByUsername(user.getUsername());
		if (!validTokens.isEmpty()) {
			validTokens.forEach(t -> {
				t.setExpired(true);
				t.setRevoked(true);
			});
			tokenRepository.saveAll(validTokens);
		}
	}

	private void saveToken(User user, String jwtToken) {
		Token token = Token.builder().token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false)
				.username(user.getUsername()).build();
		tokenRepository.save(token);
	}

	public void refreshToken(String refreshToken, HttpServletResponse response) throws IOException {
		if (refreshToken != null) {
			final String username = jwtService.extractUsername(refreshToken);
			if (username != null) {
				User user = userRepository.findByEmail(username).get();
				if (jwtService.isTokenValid(refreshToken, user)) {
					String accessToken = jwtService.generateToken(user);
					TokenVO authResponse = new TokenVO(accessToken, null);
					new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
				}
			}
		}
	}
}
