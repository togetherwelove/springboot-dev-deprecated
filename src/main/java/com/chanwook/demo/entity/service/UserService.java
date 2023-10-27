package com.chanwook.demo.entity.service;

import org.springframework.stereotype.Service;

import com.chanwook.demo.entity.Users;
import com.chanwook.demo.entity.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public Users findByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
}
