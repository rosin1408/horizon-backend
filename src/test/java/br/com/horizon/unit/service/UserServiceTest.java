package br.com.horizon.unit.service;

import static org.mockito.Mockito.verify;

import br.com.horizon.model.User;
import br.com.horizon.repository.UserRepository;
import br.com.horizon.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void shouldUnblockUser() {
        User user = User.builder().build();

        userService.unblockUser(user);

        user.setBlocked(false);

        verify(userRepository).save(user);
    }
}
