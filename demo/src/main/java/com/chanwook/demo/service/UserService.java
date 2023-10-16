package com.chanwook.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chanwook.demo.model.User;
import com.chanwook.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public List<User> list() {
		return userRepository.findAll();
	}
}
