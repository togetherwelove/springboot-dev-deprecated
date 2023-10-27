package com.chanwook.demo.api.auth.infra;

import java.util.Optional;

import com.chanwook.demo.api.auth.dto.User;

public interface SignupPort {
	Optional<User> addUser(User user);
}
