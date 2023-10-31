package com.tswny.init.service;

import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.User;
import com.tswny.init.repository.UserRepository;
import com.tswny.init.service.dto.UserDTO;
import com.tswny.init.util.EncryptUtil;
import com.tswny.init.web.rest.vm.UserVM;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserVM userVM) {
        User user = new User(userVM);
        String password = userVM.getPassword();
        if (!StringUtils.isNullOrEmpty(password)) {
            user.setPassword(EncryptUtil.securityPasswordEncrypt(password));
        }
        userRepository.save(user);
        return new UserDTO(user);
    }

    public UserDTO findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserDTO::new).orElse(null);
    }


    public UserDTO findByLogin(String login) {
        Optional<User> userOptional = userRepository.findFirstByLogin(login);
        return userOptional.map(UserDTO::new).orElse(null);
    }

    public void delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.ifPresent(userRepository::delete);
    }
}
