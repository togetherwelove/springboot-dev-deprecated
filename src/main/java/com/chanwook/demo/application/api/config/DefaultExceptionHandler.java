package com.chanwook.demo.application.api.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.chanwook.demo.application.api.config.common.ResponseCodeEnum;

@ControllerAdvice(basePackages = "com.chanwook.demo.app.api")
public class DefaultExceptionHandler {

	@ExceptionHandler(Exception.class)
	ResponseEntity<DefaultResponseBody> defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
		String[] messages = e.getMessage().split(":");
		String message = "";
		if (messages != null && messages.length > 0) {
			message = messages[0];
		}
		return ResponseEntity.ok()
				.body(DefaultResponseBody.builder()
						.code(ResponseCodeEnum.ERROR)
						.message(message)
						.data(null)
						.description(ResponseCodeEnum.ERROR.toString())
						.build());
	}
}
