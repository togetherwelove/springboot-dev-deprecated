package com.chanwook.demo.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chanwook.demo.auth.Token;
import com.chanwook.demo.auth.repository.custom.TokenCustom;


public interface TokenRepository extends JpaRepository<Token, Long>, TokenCustom {
	Optional<Token> findByToken(String token);

    List<Token> findByTokenAndUsernameAndRevoked(String refreshToken, String userEmail, boolean b);
}
