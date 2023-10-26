package com.chanwook.demo.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupVO {
	private String name;
	private String email;
	private String password;
	private String passwordVerify;
}
