package com.chanwook.demo.auth.repository.custom;

import java.util.List;

import com.chanwook.demo.auth.Token;

public interface TokenCustom {
	List<Token> findAllValidTokenByUsername(String email);
}