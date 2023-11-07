package com.chanwook.demo.domain.post.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chanwook.demo.domain.post.Post;
import com.chanwook.demo.domain.post.api.PostUsecase;
import com.chanwook.demo.domain.post.infra.PostPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService implements PostUsecase {

	private final PostPort postPort;
	
	@Override
	public List<Post> getList() {
		return postPort.getList();
	}

}
