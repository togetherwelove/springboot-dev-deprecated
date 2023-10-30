package com.chanwook.demo.domain.auth.api.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupCommand {
	private String name;
	private String email;
	private String password;
	private String passwordVerify;
}
