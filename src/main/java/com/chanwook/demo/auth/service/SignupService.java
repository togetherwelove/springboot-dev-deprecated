package com.chanwook.demo.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chanwook.demo.auth.api.dto.SignupRequest;
import com.chanwook.demo.user.Role;
import com.chanwook.demo.user.User;
import com.chanwook.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User signup(SignupRequest req) {
		if (req.getPassword().equals(req.getPasswordVerify())) {
			return userRepository.save(userMapper(req));
		} else {
			throw new RuntimeException("비밀번호가 맞지 않습니다.");
		}
	}

	private User userMapper(SignupRequest req) {
		return User.builder()
	            .email(req.getEmail())
	            .password(passwordEncoder.encode(req.getPassword()))
	            .name(req.getName())
	            .role(Role.user)
	            .build();
	}

}
