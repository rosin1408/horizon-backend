package br.com.horizon.service.service;

import br.com.horizon.model.security.TokenType;
import br.com.horizon.model.security.User;
import br.com.horizon.model.security.UserToken;
import br.com.horizon.repository.security.UserTokenRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    public UserToken createUserTokenConfirm(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime validAt = LocalDateTime.now().plusHours(5);
        var userToken = UserToken.builder().tokenType(TokenType.CONFIRM).user(user).uuid(uuid.toString()).validAt(validAt).build();

        userToken = userTokenRepository.save(userToken);

        return userToken;
    }


    public Optional<UserToken> findUserTokenByUuid(String token) {
        return userTokenRepository.findUserTokenByUuid(token, LocalDateTime.now());
    }

    public void useToken(UserToken userToken) {
        userToken.setValidAt(LocalDateTime.now());

        userTokenRepository.save(userToken);
    }
}
