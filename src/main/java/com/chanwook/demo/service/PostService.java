package com.chanwook.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.chanwook.demo.model.Post;
import com.chanwook.demo.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	public List<Post> list() {
		return postRepository.findAllByOrderByIdDesc();
	}

	public List<Post> list(String title) {
		return postRepository.findAllByTitleContainsOrderByIdDesc(title);
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
