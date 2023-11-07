package com.chanwook.demo.application.infra.auth.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.chanwook.demo.application.infra.auth.repository.entity.QTokens;
import com.chanwook.demo.application.infra.auth.repository.entity.Tokens;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenCustomImpl implements TokenCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Tokens> findAllValidTokenByUsername(String email) {
		QTokens t = QTokens.tokens;

		JPAQuery<Tuple> query = queryFactory
				.select(t.id, t.token, t.tokenType, t.expired, t.revoked, t.username)
				.from(t)
				.where(t.username.eq(email), t.expired.isFalse(), t.revoked.isFalse());

		return query.fetch()
				.stream().map(tuple -> Tokens.builder()
						.id(tuple.get(t.id))
						.username(tuple.get(t.username))
						.token(tuple.get(t.token))
						.tokenType(tuple.get(t.tokenType))
						.expired(tuple.get(t.expired))
						.revoked(tuple.get(t.revoked))
						.build())
				.collect(Collectors.toList());
	}

}
