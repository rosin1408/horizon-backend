package br.com.horizon.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.horizon.dto.SignUpRequest;
import br.com.horizon.email.MailSender;
import br.com.horizon.exception.BadRequestException;
import br.com.horizon.model.security.Role;
import br.com.horizon.model.security.RoleName;
import br.com.horizon.model.security.User;
import br.com.horizon.model.security.UserToken;
import br.com.horizon.repository.security.RoleRepository;
import br.com.horizon.repository.security.UserRepository;
import br.com.horizon.service.service.AuthService;
import br.com.horizon.service.service.UserService;
import br.com.horizon.service.service.UserTokenService;
import br.com.horizon.velocity.VelocityCompiler;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceTest {

    private static final String USER_PASSWORD = "user-password";

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserTokenService userTokenService;
    @Mock
    private UserService userService;
    @Mock
    private MailSender mailSender;
    @Mock
    private VelocityCompiler velocityCompiler;
    @InjectMocks
    private AuthService authService;

    private User userAccount;

    @BeforeEach
    public void mockRoleRepository() {
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(Role.builder().id(1L).name(RoleName.ROLE_USER).build()));
    }

    @BeforeEach
    public void mockUserRepository() {
        userAccount = new User();
        when(userRepository.save(any(User.class))).thenReturn(userAccount);
    }

    @BeforeEach
    public void mockMailSender() {
        doReturn(mailSender).when(mailSender).from(any());
        doReturn(mailSender).when(mailSender).to(any());
        doReturn(mailSender).when(mailSender).subject(any());
        doReturn(mailSender).when(mailSender).html(any());
    }

    @BeforeEach
    public void mockUserToken() {
        when(userTokenService.createUserTokenConfirm(userAccount)).thenReturn(UserToken.builder().user(userAccount).uuid("user_token_uuid").build());
    }

    @BeforeEach
    public void mockPasswordEncoder() {
        when(passwordEncoder.encode(USER_PASSWORD)).thenReturn(new BCryptPasswordEncoder().encode(USER_PASSWORD));
    }

    @Test
    public void shouldCreateUserAccount() throws InterruptedException, IOException, URISyntaxException {
        var userData = SignUpRequest.builder().email("user@email.com").name("User Name").username("username").password(USER_PASSWORD).build();

        userAccount = authService.createUsersAccount(userData);

        assertThat(userAccount).isNotNull();
    }

    @Test
    public void shouldEncriptPassword() throws InterruptedException, IOException, URISyntaxException {
        var userData = SignUpRequest.builder().email("user@email.com").name("User Name").username("username").password(USER_PASSWORD).build();

        userAccount = authService.createUsersAccount(userData);

        verify(passwordEncoder).encode(USER_PASSWORD);
    }

    @Test
    public void shouldThrowsExceptionWhenHasNoRoleInDatabase() {
        var userData = SignUpRequest.builder().email("user@email.com").name("User Name").username("username").password(USER_PASSWORD).build();
        doReturn(Optional.empty()).when(roleRepository).findByName(any());

        assertThatThrownBy(() -> authService.createUsersAccount(userData));
    }

    @Test
    public void shouldSendEmail() throws InterruptedException, IOException, URISyntaxException {
        var userData = SignUpRequest.builder().email("user@email.com").name("User Name").username("username").password(USER_PASSWORD).build();

        userAccount = authService.createUsersAccount(userData);

        verify(mailSender).send();
    }

    @Test
    public void shouldSendEmailWithHtmlFromTemplate() throws InterruptedException, IOException, URISyntaxException {
        var userData = SignUpRequest.builder().email("user@email.com").name("User Name").username("username").password(USER_PASSWORD).build();

        when(velocityCompiler.compile(anyString(), anyMap())).thenReturn("Returned String from template");
        userAccount = authService.createUsersAccount(userData);

        verify(mailSender).html("Returned String from template");
    }

    @Test
    public void shouldSendEmailWithUserData() throws InterruptedException, IOException, URISyntaxException {
        var userData = SignUpRequest.builder().email("user@email.com").name("User Name").username("username").password(USER_PASSWORD).build();
        when(userRepository.save(any(User.class))).thenReturn(userAccount);

        userAccount = authService.createUsersAccount(userData);

        verify(mailSender).to(userAccount.getEmail());
    }

    @Test
    public void shouldConfirmEmail() {
        User user = User.builder().build();
        UserToken userToken = UserToken.builder().user(user).build();
        doReturn(Optional.of(userToken)).when(userTokenService).findUserTokenByUuid("user_token");

        final var confirmed = authService.confirmEmail("user_token");
        assertThat(confirmed).isTrue();
    }

    @Test
    public void shouldMarkTokenAsUsed() {
        User user = User.builder().build();
        UserToken userToken = UserToken.builder().user(user).build();
        doReturn(Optional.of(userToken)).when(userTokenService).findUserTokenByUuid("user_token");

        authService.confirmEmail("user_token");

        verify(userTokenService).useToken(userToken);
    }

    @Test
    public void shouldUnblockUser() {
        User user = User.builder().build();
        UserToken userToken = UserToken.builder().user(user).build();
        doReturn(Optional.of(userToken)).when(userTokenService).findUserTokenByUuid("user_token");

        authService.confirmEmail("user_token");

        verify(userService).unblockUser(user);
    }

    @Test
    public void shouldThrowExceptionWhenTokenNotFound() {
        doReturn(Optional.empty()).when(userTokenService).findUserTokenByUuid(anyString());

        org.junit.jupiter.api.Assertions.assertThrows(BadRequestException.class, () -> authService.confirmEmail(""));
    }

    @Test
    public void shouldNotThrowsExceptionWhenExceptionOnMailSender() throws InterruptedException, IOException, URISyntaxException {
        when(mailSender.send()).thenThrow(IOException.class);

        var userData = SignUpRequest.builder().email("user@email.com").name("User Name").username("username").password(USER_PASSWORD).build();


        Assertions.assertThatCode(() -> authService.createUsersAccount(userData)).doesNotThrowAnyException();
    }
}
