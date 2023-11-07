package com.chanwook.demo.application.api.post;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.domain.post.Post;
import com.chanwook.demo.domain.post.api.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostRestController {
	
	private final PostService postService;
	
	@Operation(summary = "Post 목록 조회", description = "Post 목록을 조회합니다.", security = {
			@SecurityRequirement(name = "bearer-key") }, tags = { "게시판" })
	@GetMapping("/list")
	public ResponseEntity<List<Post>> getList() {
		List<Post> postList = postService.getList();
		return ResponseEntity.ok().body(postList);
	}
}
