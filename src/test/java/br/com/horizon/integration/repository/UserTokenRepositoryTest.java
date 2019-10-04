package br.com.horizon.integration.repository;

import static org.assertj.core.api.Assertions.*;

import br.com.horizon.model.Role;
import br.com.horizon.model.RoleName;
import br.com.horizon.model.TokenType;
import br.com.horizon.model.User;
import br.com.horizon.model.UserToken;
import br.com.horizon.repository.RoleRepository;
import br.com.horizon.repository.UserRepository;
import br.com.horizon.repository.UserTokenRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTokenRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;

    @AfterEach
    public void clearDatabase() {
        userTokenRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void shouldFindTokenWhenNotOverdue() {
        createUserOnDatabase(LocalDateTime.now().plusHours(1));

        Optional<UserToken> userToken = userTokenRepository.findUserTokenByUuid("user_token_uuid", LocalDateTime.now());

        assertThat(userToken.isPresent()).isTrue();
    }

    @Test
    public void shouldNotFindTokenWhenOverdue() {
        createUserOnDatabase(LocalDateTime.now());

        Optional<UserToken> userToken = userTokenRepository.findUserTokenByUuid("user_token_uuid", LocalDateTime.now().plusSeconds(1));

        assertThat(userToken.isPresent()).isFalse();
    }

    private void createUserOnDatabase(LocalDateTime validAt) {
        var role = Role.builder().name(RoleName.ROLE_ADMIN).build();
        roleRepository.save(role);

        var user = User.builder()
                       .name("Test User")
                       .email("test@test.com.br")
                       .password("test_passord")
                       .username("test_username")
                       .blocked(true)
                       .roles(Collections.singleton(role))
                       .build();

        userRepository.save(user);

        var userToken = UserToken.builder().tokenType(TokenType.CONFIRM).user(user).uuid("user_token_uuid").validAt(validAt).build();
        userTokenRepository.save(userToken);
    }
}
