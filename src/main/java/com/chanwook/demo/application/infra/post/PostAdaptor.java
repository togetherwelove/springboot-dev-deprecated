package com.chanwook.demo.application.infra.post;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chanwook.demo.application.infra.post.repository.PostRepository;
import com.chanwook.demo.domain.post.Post;
import com.chanwook.demo.domain.post.infra.PostPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostAdaptor implements PostPort {
	
	private final PostRepository postRepository;

	@Override
	public List<Post> getList() {
		return postRepository.findAll().stream().map(p -> Post.builder()
				.id(p.getId())
				.author(p.getAuthor())
				.title(p.getTitle())
				.content(p.getContent())
				.build()).collect(Collectors.toList());
	}

}
