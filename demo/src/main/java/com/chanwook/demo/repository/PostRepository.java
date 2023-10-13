package com.chanwook.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chanwook.demo.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	// query creation
	List<Post> findAllByOrderByIdDesc();

	List<Post> findAllByTitleContainsOrderByIdDesc(String title);

}
