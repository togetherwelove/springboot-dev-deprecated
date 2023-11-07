package com.chanwook.demo.application.infra.config;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QueryDslConfig {

	private final EntityManager em;

    @Bean
    JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(em);
	}
}
