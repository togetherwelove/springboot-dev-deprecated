package com.chanwook.demo.domain.post.infra;

import java.util.List;

import com.chanwook.demo.domain.post.Post;

public interface PostPort {
	List<Post> getList();
}
