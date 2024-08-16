package com.tswny.init.security;

import com.tswny.init.domain.User;
import com.tswny.init.repository.UserRepository;
import com.tswny.init.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserHelper {
    private final UserRepository userRepository;

    public UserHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        return userRepository.findFirstByLogin(login.get()).orElse(null);
    }
}
