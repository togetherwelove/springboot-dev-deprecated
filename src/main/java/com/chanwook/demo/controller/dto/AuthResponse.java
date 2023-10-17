package com.chanwook.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	private String accessToken;
	private String refreshToken;

	public AuthResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}
