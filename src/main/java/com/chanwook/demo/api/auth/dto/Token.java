package com.chanwook.demo.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
	private String accessToken;
	private String refreshToken;
}
