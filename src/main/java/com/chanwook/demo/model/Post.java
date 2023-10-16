package com.chanwook.demo.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {
	private String title;
	private String content;
	private String author;

	public void update(Post post) {
		this.title = post.title;
		this.content = post.content;
		this.author = post.author;
	}
}
