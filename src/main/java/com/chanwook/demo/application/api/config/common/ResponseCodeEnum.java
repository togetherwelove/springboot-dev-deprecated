package com.chanwook.demo.application.api.config.common;

public enum ResponseCodeEnum {
	SUCCESS("success"),
	ERROR("fail");

	private final String description;

	ResponseCodeEnum(final String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}
}
