package com.chanwook.demo.repository;

import java.util.List;

import com.chanwook.demo.model.auth.Token;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenCustomImpl implements TokenCustom{

	private final JPAQueryFactory queryFactory;
	@Override
	public List<Token> findAllValidTokenByUsername(String email) {
		return null;
	}

}
