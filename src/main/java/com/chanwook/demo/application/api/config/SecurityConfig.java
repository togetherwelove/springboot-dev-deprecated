package com.chanwook.demo.application.api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.chanwook.demo.application.api.config.filter.JwtAuthenticationFilter;
import com.chanwook.demo.application.api.config.service.LogoutService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final LogoutService logoutService;
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
		.cors(Customizer.withDefaults())
		.csrf(csrf -> csrf
			.ignoringAntMatchers("/h2-console/**").disable())

		.authorizeHttpRequests((authorizeRequest) -> authorizeRequest
			.antMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()
			.anyRequest().authenticated())

		.sessionManagement((sessionManagement) -> sessionManagement
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

		.authenticationProvider(authenticationProvider)

		.logout((logoutConfig) -> logoutConfig
			.logoutUrl("/auth/logout")
			.addLogoutHandler(logoutService)
			.logoutSuccessHandler((request, response, authentication) ->
				SecurityContextHolder.clearContext()))

		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

		.headers((headers) -> headers
			.frameOptions().disable());

		return http.build();
	}

	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
