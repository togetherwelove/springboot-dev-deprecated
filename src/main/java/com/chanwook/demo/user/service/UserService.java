package com.chanwook.demo.user.service;

import org.springframework.stereotype.Service;

import com.chanwook.demo.user.User;
import com.chanwook.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
}
