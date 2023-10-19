package com.chanwook.demo.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chanwook.demo.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	// query creation
	Page<Post> findAllByOrderByIdDesc(Pageable pageable);

	List<Post> findAllByTitleContainsOrderByIdDesc(String title);

}
