package com.chanwook.demo.app.infra.auth.repository.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Codes extends BaseEntity {
	
	private String code;
	private String email;
	private boolean expired;

}
