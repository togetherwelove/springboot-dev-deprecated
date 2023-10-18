package com.chanwook.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.chanwook.demo.user.Role;
import com.chanwook.demo.user.User;
import com.chanwook.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		User user = User.builder()
				.email("user@user.dev")
				.password(passwordEncoder.encode("1234"))
				.name("강찬욱")
				.role(Role.user)
				.build();
		try {			
			userRepository.save(user);
		} catch (Exception e) {
 			System.err.println(user.getEmail() + " 이미 생성 됨");
		}
	}
}
