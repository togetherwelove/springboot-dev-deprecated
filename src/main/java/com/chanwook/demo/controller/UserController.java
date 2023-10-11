package com.chanwook.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.model.Role;
import com.chanwook.demo.model.Users;
import com.chanwook.demo.repository.UserRepository;
import com.chanwook.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public List<Users> userList() {
        return userService.findAll();
    }

    @PostMapping
    public void save() {
        Users user = Users.builder()
                    .email("rkdcksdnr1@gmail.com")
                    .password(passwordEncoder.encode("Gg070707!"))
                    .name("강찬욱")
                    .role(Role.User)
                    .address("경기도 호현로 49번길 35 대현빌라 가동 202호")
                    .build();
		try { 
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
