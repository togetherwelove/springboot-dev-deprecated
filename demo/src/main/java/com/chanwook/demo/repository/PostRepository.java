package com.chanwook.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chanwook.demo.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {}
