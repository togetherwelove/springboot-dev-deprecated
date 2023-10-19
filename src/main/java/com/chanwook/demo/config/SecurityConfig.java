package com.chanwook.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chanwook.demo.config.entrypoint.JwtAuthenticationEntryPoint;
import com.chanwook.demo.config.filter.JwtAuthenticationFilter;
import com.chanwook.demo.config.service.LogoutService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final LogoutService logoutService;
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**").disable())

				.authorizeHttpRequests((authorizeRequest) -> authorizeRequest
						.antMatchers("/",  "/auth/**", "/h2-console/**").permitAll()
						.anyRequest().authenticated())

				.authenticationProvider(authenticationProvider)

				.sessionManagement(
						(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(exceptionHeadler -> exceptionHeadler.authenticationEntryPoint(jwtAuthenticationEntryPoint))

				.logout(logoutConfig -> {
					logoutConfig
					.logoutUrl("/auth/logout")
					.addLogoutHandler(logoutService)
					.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
				})
				.headers((headers) -> headers.frameOptions().disable());
		return http.build();
	}
}
