package com.chanwook.demo.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class User {
	private Long id;
	private String email;
	private String password;
	private String name;
}
