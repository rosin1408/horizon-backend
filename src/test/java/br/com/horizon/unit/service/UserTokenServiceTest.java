package br.com.horizon.unit.service;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import br.com.horizon.model.TokenType;
import br.com.horizon.model.User;
import br.com.horizon.model.UserToken;
import br.com.horizon.repository.UserTokenRepository;
import br.com.horizon.service.UserTokenService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserTokenServiceTest {

    @Mock
    private UserTokenRepository userTokenRepository;

    @InjectMocks
    private UserTokenService userTokenService;

    @Test
    public void shouldCreateUserToken() {
        var user = User.builder().build();
        LocalDateTime validAt = LocalDateTime.now().plusHours(5);
        var userToken = UserToken.builder().tokenType(TokenType.CONFIRM).user(user).uuid("1234567890").validAt(validAt).build();

        userTokenService.createUserTokenConfirm(any());

        verify(userTokenRepository).save(any());
    }

    @Test
    public void shouldFindUserTokenByUuid() {
        userTokenService.findUserTokenByUuid("token_uuid");

        verify(userTokenRepository).findUserTokenByUuid(anyString(), any(LocalDateTime.class));
    }

    @Test
    public void shouldUseToken() {
        var userToken = UserToken.builder().validAt(LocalDateTime.now().plusHours(5)).build();

        userTokenService.useToken(userToken);

        userToken.setValidAt(LocalDateTime.now());

        verify(userTokenRepository).save(userToken);
    }
}
