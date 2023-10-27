package com.chanwook.demo.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chanwook.demo.entity.Tokens;
import com.chanwook.demo.entity.repository.custom.TokenCustom;


public interface TokenRepository extends JpaRepository<Tokens, Long>, TokenCustom {
	Optional<Tokens> findByToken(String token);

    List<Tokens> findByTokenAndUsernameAndRevoked(String refreshToken, String userEmail, boolean b);
}
