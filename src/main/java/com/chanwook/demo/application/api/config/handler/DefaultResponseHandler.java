package com.chanwook.demo.application.api.config.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.chanwook.demo.application.api.config.common.ResponseCodeEnum;
import com.chanwook.demo.application.api.config.handler.DefaultResponseBody.DefaultResponseBodyBuilder;

@ControllerAdvice(basePackages = "com.chanwook.demo.application.api")
public class DefaultResponseHandler implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		DefaultResponseBodyBuilder builder = DefaultResponseBody.builder();
		if (body != null && (body instanceof DefaultResponseBody)) {
			DefaultResponseBody defaultBody = (DefaultResponseBody) body;
			if (defaultBody.getCode().equals(ResponseCodeEnum.ERROR)) {
				builder
					.code(defaultBody.getCode())
					.message(defaultBody.getMessage())
					.data("")
					.description(defaultBody.getDescription());
			}
		} else {
			builder
				.code(ResponseCodeEnum.SUCCESS)
				.message(ResponseCodeEnum.SUCCESS.toString())
				.data(body);
		}
		return builder.build();
	}

}
