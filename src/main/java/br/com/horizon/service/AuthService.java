package br.com.horizon.service;

import br.com.horizon.dto.SignUpRequest;
import br.com.horizon.email.MailSender;
import br.com.horizon.exception.AppException;
import br.com.horizon.model.Role;
import br.com.horizon.model.RoleName;
import br.com.horizon.model.User;
import br.com.horizon.model.UserToken;
import br.com.horizon.repository.RoleRepository;
import br.com.horizon.repository.UserRepository;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private MailSender mailSender;

    public URI createUsersAccount(@RequestBody @Valid SignUpRequest signUpRequest) throws InterruptedException, IOException, URISyntaxException {
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        saveUserTokenAndSendEmail(user);

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}").buildAndExpand(result.getUsername()).toUri();
    }

    private void saveUserTokenAndSendEmail(User user) {
        UserToken userToken = userTokenService.createUserTokenConfirm(user);

        Runnable sendMail = () -> {
            var htmlEmail = "<a href=\"http://localhost:8080/auth/confirm?tk=${TOKEN}\">Confirmar e-mail</a>";
            try {
                mailSender.from("rosin1408@gmail.com")
                          .to(user.getEmail())
                          .subject("Confirmação de email")
                          .html(htmlEmail.replace("${TOKEN}", userToken.getUuid()))
                          .send();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        sendMail.run();
    }

    public boolean confirmEmail(String tk) {
        Optional<UserToken> userTokenByUuid = userTokenService.findUserTokenByUuid(tk);

        return userTokenByUuid.isPresent();
    }
}
