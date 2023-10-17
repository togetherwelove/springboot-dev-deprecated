package com.chanwook.demo.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.model.auth.User;
import com.chanwook.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/list")
	public ResponseEntity<List<User>> list() {
		return ResponseEntity.ok(userService.list());
	}
}
