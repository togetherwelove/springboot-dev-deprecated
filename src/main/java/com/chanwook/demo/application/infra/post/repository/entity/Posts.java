package com.chanwook.demo.application.infra.post.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.chanwook.demo.application.infra.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Posts extends BaseEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id private Long id;

	private String title;

	private String content;

	private String author;

}
