package com.chanwook.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chanwook.demo.model.Post;
import com.chanwook.demo.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/list")
	public List<Post> list(@RequestParam(required = false) String title) {
		if (title == null) {
			return postService.list();
		} else {
			return postService.list(title);
		}
	}

	@GetMapping("/{id}")
	public Post get(@PathVariable Long id) {
		return postService.get(id);
	}

	@PostMapping("/add")
	public ResponseEntity<Post> add(@RequestBody Post post) {
		ResponseEntity<Post> entity = null;
		try {
			postService.add(post);
			entity = new ResponseEntity<Post>(post, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Post>(post, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@DeleteMapping("/{id}")
	public void remove(@PathVariable Long id) {
		try {
			postService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post post) {
		ResponseEntity<Post> entity = null;
		try {
			postService.update(id, post);
			entity = new ResponseEntity<Post>(post, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Post>(post, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

}
