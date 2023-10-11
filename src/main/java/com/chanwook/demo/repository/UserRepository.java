package com.chanwook.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chanwook.demo.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long userId);
    List<Users> findAll();
}
