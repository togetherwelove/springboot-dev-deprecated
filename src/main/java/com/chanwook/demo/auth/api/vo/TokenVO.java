package com.chanwook.demo.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {
	private String accessToken;
	private String refreshToken;
}
