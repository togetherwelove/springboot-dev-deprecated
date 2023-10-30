package com.chanwook.demo.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.chanwook.demo.app.api.config.QueryDslConfig;
import com.chanwook.demo.app.infra.auth.UserSignupCommandAdaptor;
import com.chanwook.demo.app.infra.auth.repository.UserRepository;
import com.chanwook.demo.app.infra.auth.repository.entity.Users;
import com.chanwook.demo.domain.auth.User;

@DataJpaTest
@ImportAutoConfiguration({ QueryDslConfig.class, UserSignupCommandAdaptor.class })
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserSignupCommandAdaptorTest {

	@InjectMocks
	UserSignupCommandAdaptor userSignupCommandAdaptor;

	@Mock
	UserRepository userRepository;

	@Test
	void addUserTest() {
		User newUser = User.builder()
				.email("user@user.dev")
				.password("qwer1234")
				.name("dean")
				.build();

		Optional<User> savedUser = userSignupCommandAdaptor.addUser(newUser);

		assertTrue(savedUser.isPresent());
		// TODO
		// return false

		userRepository.delete(usersTestMapper.apply(savedUser.get()));
	}

	Function<User, Users> usersTestMapper = user -> Users.builder().id(user.getId()).build();

}
