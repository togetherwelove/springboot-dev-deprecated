package com.chanwook.demo.post;

import javax.persistence.Entity;

import com.chanwook.demo.model.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {
	private String title;
	private String content;
	private String author;

	private String username;

	public void update(Post post) {
		this.title = post.title;
		this.content = post.content;
		this.author = post.author;
	}
}
