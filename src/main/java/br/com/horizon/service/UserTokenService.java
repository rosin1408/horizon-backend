package br.com.horizon.service;

import br.com.horizon.model.TokenType;
import br.com.horizon.model.User;
import br.com.horizon.model.UserToken;
import br.com.horizon.repository.UserTokenRepository;
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
        var userToken = UserToken.builder().tokenType(TokenType.CONFIRM).user(user).uuid(uuid.toString()).build();

        userToken = userTokenRepository.save(userToken);

        return userToken;
    }

    public Optional<UserToken> findUserTokenByUuid(String token) {
        return userTokenRepository.findUserTokenByUuid(token);
    }
}
