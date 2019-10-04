package br.com.horizon.service;

import br.com.horizon.dto.SignUpRequest;
import br.com.horizon.email.MailSender;
import br.com.horizon.exception.AppException;
import br.com.horizon.exception.BadRequestException;
import br.com.horizon.model.Role;
import br.com.horizon.model.RoleName;
import br.com.horizon.model.User;
import br.com.horizon.model.UserToken;
import br.com.horizon.repository.RoleRepository;
import br.com.horizon.repository.UserRepository;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private UserService userService;

    @Autowired
    private MailSender mailSender;

    @Transactional
    public User createUsersAccount(SignUpRequest signUpRequest) throws InterruptedException, IOException, URISyntaxException {
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        saveUserTokenAndSendEmail(result);

        return result;
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
            } catch (URISyntaxException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        };

        sendMail.run();
    }

    @Transactional
    public boolean confirmEmail(String tk) throws BadRequestException {
        Optional<UserToken> userTokenOpt = userTokenService.findUserTokenByUuid(tk);

        userTokenOpt.ifPresentOrElse(userToken -> {
            userTokenService.useToken(userToken);
            userService.unblockUser(userToken.getUser());
        }, () -> {
            throw new BadRequestException("Invalid token");
        });

        return userTokenOpt.isPresent();
    }
}
