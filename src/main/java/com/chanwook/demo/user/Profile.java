package com.chanwook.demo.user;

import javax.persistence.Entity;

import com.chanwook.demo.model.BaseEntity;

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
}
