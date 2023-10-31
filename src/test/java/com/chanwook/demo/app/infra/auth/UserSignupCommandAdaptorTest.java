package com.chanwook.demo.app.infra.auth;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.chanwook.demo.app.api.config.QueryDslConfig;
import com.chanwook.demo.app.infra.auth.repository.UserRepository;
import com.chanwook.demo.app.infra.auth.repository.entity.Users;
import com.chanwook.demo.domain.auth.User;

@DataJpaTest
@ImportAutoConfiguration({ QueryDslConfig.class, UserSignupCommandAdaptor.class })
public class UserSignupCommandAdaptorTest {

	@Autowired
	UserSignupCommandAdaptor userSignupCommandAdaptor;

	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("사용자 추가 기능 테스트")
	void addUserTest() {
		User newUser = User.builder()
				.email("user@user.dev")
				.password("qwer1234")
				.name("dean")
				.build();

		Optional<User> savedUser = userSignupCommandAdaptor.addUser(newUser);

		assertTrue(savedUser.isPresent());

		userRepository.delete(usersTestMapper.apply(savedUser.get()));
	}

	Function<User, Users> usersTestMapper = user -> Users.builder().id(user.getId()).build();

}
