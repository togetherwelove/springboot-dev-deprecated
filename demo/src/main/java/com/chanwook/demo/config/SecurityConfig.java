package com.chanwook.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**").disable())
				.authorizeHttpRequests((authorizeRequest) -> authorizeRequest
						.antMatchers("/", "/auth/**", "/h2-console/**").permitAll()
						.anyRequest().authenticated())
				.authenticationProvider(authenticationProvider)
				.headers((headers) -> headers.frameOptions().disable())
				.httpBasic();
		return http.build();
	}
}
