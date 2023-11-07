package com.chanwook.demo.application.api.auth.service.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthVO {
	private String accessToken;
	private String refreshToken;
}
