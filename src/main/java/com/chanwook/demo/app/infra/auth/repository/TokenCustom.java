package com.chanwook.demo.app.infra.auth.repository;

import java.util.List;

import com.chanwook.demo.app.infra.auth.repository.entity.Tokens;

public interface TokenCustom {
	List<Tokens> findAllValidTokenByUsername(String email);
}
