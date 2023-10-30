package com.chanwook.demo.app.api.auth.service;

import org.springframework.stereotype.Service;

import com.chanwook.demo.app.infra.auth.repository.UserRepository;
import com.chanwook.demo.app.infra.auth.repository.entity.Users;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public Users findByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
}
