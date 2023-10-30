package com.chanwook.demo.app.infra.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chanwook.demo.app.infra.auth.repository.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{
	Optional<Users> findByEmail(String email);
}
