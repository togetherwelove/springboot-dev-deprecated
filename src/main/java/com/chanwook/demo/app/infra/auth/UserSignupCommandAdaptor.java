package com.chanwook.demo.app.infra.auth;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.chanwook.demo.app.infra.auth.repository.UserRepository;
import com.chanwook.demo.app.infra.auth.repository.entity.Users;
import com.chanwook.demo.domain.auth.User;
import com.chanwook.demo.domain.auth.infra.UserSignupCommandPort;

@Component
public class UserSignupCommandAdaptor implements UserSignupCommandPort {

	private final UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserSignupCommandAdaptor(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public Optional<User> addUser(User user) {
		Users users = userRepository.save(usersMapper.apply(user));
		return ObjectUtils.isEmpty(users) ? Optional.empty() : Optional.of(userMapper.apply(users));
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		Users users = userRepository.findByEmail(email).get();
		return ObjectUtils.isEmpty(users) ? Optional.empty() : Optional.of(userMapper.apply(users));
	}

    Function<User, Users> usersMapper = user -> Users.builder()
            .email(user.getEmail())
            .password(passwordEncoder.encode(user.getPassword()))
            .name(user.getName())
            .build();

    Function<Users, User> userMapper = users -> User.builder()
            .id(users.getId())
            .email(users.getEmail())
            .name(users.getName())
            .build();

}
