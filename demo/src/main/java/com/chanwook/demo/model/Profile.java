package com.chanwook.demo.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends BaseEntity {
	private String name;
	private byte[] data;
	private String mimeType;
	private Long size;
	
	@OneToOne
	private User user;
}
