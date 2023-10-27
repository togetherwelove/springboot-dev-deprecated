package com.chanwook.demo.entity.repository.custom;

import java.util.List;

import com.chanwook.demo.entity.Tokens;

public interface TokenCustom {
	List<Tokens> findAllValidTokenByUsername(String email);
}
