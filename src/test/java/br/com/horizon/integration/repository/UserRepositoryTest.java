package br.com.horizon.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.horizon.model.security.Role;
import br.com.horizon.model.security.RoleName;
import br.com.horizon.model.security.User;
import br.com.horizon.repository.security.RoleRepository;
import br.com.horizon.repository.security.UserRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    public void cleanDatabase() {
        userRepository.deleteAll();
        roleRepository.deleteAll();

    }

    @Test
    public void shouldFindUserToLoginWhenUserIsNotBlocked() {
        createUserOnDatabase(false);

        Optional<User> userFromQuery = userRepository.findUserToLogin("test_username");

        assertThat(userFromQuery.isPresent()).isTrue();
    }

    @Test
    public void shouldNotFindUserToLoginWhenUserIsNotBlocked() {
        createUserOnDatabase(true);

        Optional<User> userFromQuery = userRepository.findUserToLogin("test_username");

        assertThat(userFromQuery.isPresent()).isFalse();
    }

    @Test
    public void shouldFindUserToLoginByUsername() {
        createUserOnDatabase(true);

        Optional<User> userFromQuery = userRepository.findUserToLogin("test_username");

        assertThat(userFromQuery.isPresent()).isFalse();
    }

    @Test
    public void shouldFindUserToLoginByEmail() {
        createUserOnDatabase(true);

        Optional<User> userFromQuery = userRepository.findUserToLogin("test@test.com.br");

        assertThat(userFromQuery.isPresent()).isFalse();
    }

    private void createUserOnDatabase(boolean blocked) {
        var role = Role.builder().name(RoleName.ROLE_ADMIN).build();
        roleRepository.save(role);

        var user = User.builder()
                       .name("Test User")
                       .email("test@test.com.br")
                       .password("test_passord")
                       .username("test_username")
                       .blocked(blocked)
                       .roles(Collections.singleton(role))
                       .build();

        userRepository.save(user);
    }
}
