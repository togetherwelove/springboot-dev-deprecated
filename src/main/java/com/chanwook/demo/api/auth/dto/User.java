package com.chanwook.demo.api.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
	private Long id;
	private String email;
	private String password;
	private String name;
}
