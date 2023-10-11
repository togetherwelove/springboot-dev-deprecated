package com.chanwook.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chanwook.demo.model.Users;
import com.chanwook.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<Users> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }
    
}
