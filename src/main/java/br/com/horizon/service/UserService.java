package br.com.horizon.service;

import br.com.horizon.model.User;
import br.com.horizon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void unblockUser(User user) {
        user.setBlocked(false);

        userRepository.save(user);
    }
}
