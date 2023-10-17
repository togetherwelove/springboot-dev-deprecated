package com.chanwook.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chanwook.demo.model.auth.Token;


public interface TokenRepository extends JpaRepository<Token, Long>, TokenCustom {
	Optional<Token> findByToken(String token);

    List<Token> findByTokenAndUsernameAndRevoked(String refreshToken, String userEmail, boolean b);
}
