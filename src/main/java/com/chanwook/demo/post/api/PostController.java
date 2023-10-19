package com.chanwook.demo.post.api;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.post.Post;
import com.chanwook.demo.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping()
	public ResponseEntity<Page<Post>> list(Pageable pageable) {
		return ResponseEntity.ok().body(postService.list(pageable));
	}

	@PostMapping()
	public ResponseEntity<Post> add(@RequestBody Post req, Principal principal) {

		Post post = postService.add(req, principal.getName());

		return ResponseEntity.ok().body(post);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> get(@PathVariable Long id) {
		Post post = postService.get(id);
		return ResponseEntity.ok().body(post);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post post) {
		postService.update(id, post);
		return ResponseEntity.ok().body(post);
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable Long id) {
		postService.delete(id);
	}
}
