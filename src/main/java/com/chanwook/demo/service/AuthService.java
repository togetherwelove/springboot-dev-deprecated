package com.chanwook.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chanwook.demo.config.service.JwtService;
import com.chanwook.demo.controller.dto.AuthResponse;
import com.chanwook.demo.model.auth.Token;
import com.chanwook.demo.model.auth.TokenType;
import com.chanwook.demo.model.auth.User;
import com.chanwook.demo.repository.TokenRepository;
import com.chanwook.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final JwtService jwtService;

	public AuthResponse authenticate(User user) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		String jwtToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveToken(user, jwtToken);
		saveToken(user, refreshToken);
		return new AuthResponse(jwtToken, refreshToken);
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

	public Optional<String> refreshToken(String refreshToken, HttpServletResponse response) throws IOException {
		String accessToken = null;
		if (!ObjectUtils.isEmpty(refreshToken)) {
			final var userEmail = jwtService.extractUsername(refreshToken);
			if (!userEmail.isEmpty()) {
				var user = userRepository.findByEmail(userEmail);
				List<Token> validRefreshTokens = tokenRepository.findByTokenAndUsernameAndRevoked(refreshToken,
						userEmail, false);
				if (user.isPresent() && validRefreshTokens.size() > 0
						&& jwtService.isTokenValid(refreshToken, user.get())) {
					accessToken = jwtService.generateToken(user.get());
					saveToken(user.get(), accessToken);
				}
			}
		}
		return accessToken == null ? Optional.empty() : Optional.of(accessToken);
	}
}
