package com.chanwook.demo.application.infra.auth.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chanwook.demo.application.infra.auth.repository.entity.Tokens;

@Repository
public interface TokenCustom {
	List<Tokens> findAllValidTokenByUsername(String email);
}
