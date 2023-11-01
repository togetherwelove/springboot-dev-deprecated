package com.chanwook.demo.domain.auth.infra;

import java.util.Optional;

import com.chanwook.demo.domain.auth.User;

public interface UserSignupCommandPort {
	Optional<User> addUser(User user);
	
	Optional<User> findByEmail(String email);
}
