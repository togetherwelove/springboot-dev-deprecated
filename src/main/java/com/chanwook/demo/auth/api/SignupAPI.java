package com.chanwook.demo.auth.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.auth.api.vo.SignupVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SignupAPI {
	@PostMapping("/check")
	public void check(@RequestBody SignupVO singup) {
		
	}
}
