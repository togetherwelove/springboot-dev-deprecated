package com.chanwook.demo.api.auth.domain;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.chanwook.demo.api.auth.dto.User;
import com.chanwook.demo.api.auth.infra.SignupPort;
import com.chanwook.demo.entity.Users;
import com.chanwook.demo.entity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SignupAdapter implements SignupPort {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> addUser(User user) {
		Users users = userRepository.save(usersMapper(user));
		return ObjectUtils.isEmpty(users) ? Optional.empty() : Optional.of(userMapper(users));
	}

	private Users usersMapper(User user) {
		return Users.builder()
				.email(user.getEmail())
				.password(passwordEncoder.encode(user.getPassword()))
				.name(user.getName())
				.build();
	}

	private User userMapper(Users users) {
		return User.builder()
				.email(users.getEmail())
				.password(users.getPassword())
				.name(users.getName())
				.build();
	}
}
