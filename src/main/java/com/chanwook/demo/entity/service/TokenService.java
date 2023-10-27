package com.chanwook.demo.entity.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.chanwook.demo.api.auth.dto.Token;
import com.chanwook.demo.api.auth.service.vo.LoginRequest;
import com.chanwook.demo.api.config.service.JwtService;
import com.chanwook.demo.entity.Tokens;
import com.chanwook.demo.entity.Users;
import com.chanwook.demo.entity.define.TokenType;
import com.chanwook.demo.entity.repository.TokenRepository;
import com.chanwook.demo.entity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final JwtService jwtService;

	public Token authenticate(LoginRequest login) {

		Users user = Users.builder()
				.email(login.getEmail())
				.password(login.getPassword())
				.build();

		authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		String accessToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveToken(user, accessToken);
		saveToken(user, refreshToken);
		return new Token(accessToken, refreshToken);
	}

	private void revokeAllUserTokens(Users user) {
		List<Tokens> validTokens = tokenRepository.findAllValidTokenByUsername(user.getUsername());
		if (!validTokens.isEmpty()) {
			validTokens.forEach(t -> {
				t.setExpired(true);
				t.setRevoked(true);
				tokenRepository.save(t);
			});
		}
	}

	private void saveToken(Users user, String jwtToken) {
		Tokens token = Tokens.builder()
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.username(user.getUsername())
				.build();
		tokenRepository.save(token);
	}

	public Optional<String> refreshToken(String refreshToken, HttpServletResponse response) throws IOException {
		String accessToken = null;
		if (!ObjectUtils.isEmpty(refreshToken)) {
			final String username = jwtService.extractUsername(refreshToken);
			if (username != null) {
				var user = userRepository.findByEmail(username);
				List<Tokens> validResfreshTokens = tokenRepository.findByTokenAndUsernameAndRevoked(refreshToken, username, false);
				if (user.isPresent() && validResfreshTokens.size() > 0 && jwtService.isTokenValid(refreshToken, user.get())) {
					accessToken = jwtService.generateToken(user.get());
					saveToken(user.get(), accessToken);
				}
			}
		}
		return accessToken == null ? Optional.empty() : Optional.of(accessToken);
	}
}
