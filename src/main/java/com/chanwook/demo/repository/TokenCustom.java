package com.chanwook.demo.repository;

import java.util.List;

import com.chanwook.demo.model.auth.Token;

public interface TokenCustom {
	List<Token> findAllValidTokenByUsername(String email);
}
