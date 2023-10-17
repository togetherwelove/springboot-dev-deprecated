package com.chanwook.demo.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.chanwook.demo.model.auth.QToken;
import com.chanwook.demo.model.auth.Token;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenCustomImpl implements TokenCustom{

	private final JPAQueryFactory queryFactory;
	@Override
	public List<Token> findAllValidTokenByUsername(String email) {
		QToken t = QToken.token1;

		JPAQuery<Tuple> query = queryFactory
				.select(t.id, t.token, t.tokenType, t.expired, t.revoked, t.username)
				.from(t)
				.where(t.username.eq(email), t.expired.isFalse(), t.revoked.isFalse());

		return query.fetch()
				.stream().map(tuple ->
				Token.builder()
				.id(tuple.get(t.id))
				.username(tuple.get(t.username))
				.token(tuple.get(t.token))
				.tokenType(tuple.get(t.tokenType))
				.expired(tuple.get(t.expired))
				.revoked(tuple.get(t.revoked))
				.build()).collect(Collectors.toList());
	}

}
