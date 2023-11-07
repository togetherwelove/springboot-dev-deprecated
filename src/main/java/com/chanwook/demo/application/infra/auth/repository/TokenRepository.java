package com.chanwook.demo.application.infra.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chanwook.demo.application.infra.auth.repository.entity.Tokens;

@Repository
public interface TokenRepository extends JpaRepository<Tokens, Long>, TokenCustom {
    Optional<Tokens> findByToken(String token);

    List<Tokens> findByTokenAndUsernameAndRevoked(String refreshToken, String userEmail, boolean b);
}
