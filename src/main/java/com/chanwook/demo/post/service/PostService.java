package com.chanwook.demo.post.service;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chanwook.demo.post.Post;
import com.chanwook.demo.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	public Page<Post> list(Pageable pageable) {
		return postRepository.findAllByOrderByIdDesc(pageable);
	}

	public Post get(Long id) {
		return postRepository.findById(id).get();
	}

	public Post add(Post post, String username) {
		post.setUsername(username);
		return postRepository.save(post);
	}

	public void delete(Long id) {
		postRepository.deleteById(id);
	}

	@Transactional
	public void update(Long id, Post updPost) {
		Post orgPost = postRepository.findById(id).get();
		orgPost.update(updPost);
	}
}
