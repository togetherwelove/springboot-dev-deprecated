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
	public List<Post> list() {
		return postService.list();
	}
	
	@GetMapping("/{id}")
	public Post get(@PathVariable Long id) {
		return postService.get(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody Post post) {
		ResponseEntity<String> entity = null;
		try {			
			postService.add(post);
			entity = new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> remove(@PathVariable Long id) {
		ResponseEntity<String> entity = null;
		try {			
			postService.delete(id);
			entity = new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Post post) {
		ResponseEntity<String> entity = null;
		try {			
			postService.update(id, post);
			entity = new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

}
