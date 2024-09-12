package com.tswny.init.service;

import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.User;
import com.tswny.init.repository.UserRepository;
import com.tswny.init.service.dto.UserDTO;
import com.tswny.init.service.mapper.UserMapper;
import com.tswny.init.service.vm.SubscribeVM;
import com.tswny.init.util.EncryptUtil;
import com.tswny.init.web.rest.vm.UserVM;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserVM userVM) {
        User user = new User(userVM);
        String password = userVM.getPassword();
        if (!StringUtils.isNullOrEmpty(password)) {
            user.setPassword(EncryptUtil.securityPasswordEncrypt(password));
        }
        return userMapper.toDto(userRepository.save(user));
//        return new UserDTO(user);
    }

    public UserDTO findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(userMapper::toDto).orElse(null);
    }


    public UserDTO findByLogin(String login) {
        Optional<User> userOptional = userRepository.findFirstByLogin(login);
        return userOptional.map(userMapper::toDto).orElse(null);
    }

    public void delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.ifPresent(userRepository::delete);
    }

    public List<UserDTO> getSubscribers(String userName) {
        Optional<User> userOptional = userRepository.findFirstByLogin(userName);
        User user = userOptional.get();
        List<User> userList = new ArrayList<>();
        Set<User> subscribers = user.getSubscribers();
        userList.addAll(subscribers);
        return userList.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public void subscribe(SubscribeVM subscribeVM) {
        String subscriberName = subscribeVM.getSubscriberName();
        String publisherName = subscribeVM.getPublisherName();
        Optional<User> subscriberOptional = userRepository.findFirstByLogin(subscriberName);
        User subscriber = subscriberOptional.get();

        Optional<User> publisherOptional = userRepository.findFirstByLogin(publisherName);
        User publisher = publisherOptional.get();

        subscriber.getPublishers().add(publisher);
        // publisher.getSubscribers().add(subscriber);

        userRepository.save(subscriber);
        // userRepository.save(publisher);
    }

    public void unsubscribe(SubscribeVM subscribeVM) {
        String subscriberName = subscribeVM.getSubscriberName();
        String publisherName = subscribeVM.getPublisherName();
        Optional<User> subscriberOptional = userRepository.findFirstByLogin(subscriberName);
        User subscriber = subscriberOptional.get();

        Optional<User> publisherOptional = userRepository.findFirstByLogin(publisherName);
        User publisher = publisherOptional.get();

        subscriber.getPublishers().remove(publisher);
        // publisher.getSubscribers().remove(subscriber);

        userRepository.save(subscriber);
        // userRepository.save(publisher);
    }

    public List<UserDTO> getPublishers(String userName) {
        Optional<User> userOptional = userRepository.findFirstByLogin(userName);
        User user = userOptional.get();
        List<User> userList = new ArrayList<>();
        Set<User> publishers = user.getPublishers();
        userList.addAll(publishers);
        return userList.stream().map(userMapper::toDto).collect(Collectors.toList());
    }


    public boolean isSubscribed(SubscribeVM subscribeVM) {
        String subscriberName = subscribeVM.getSubscriberName();
        String publisherName = subscribeVM.getPublisherName();
        Optional<User> subscriberOptional = userRepository.findFirstByLogin(subscriberName);
        User subscriber = subscriberOptional.get();

        Set<User> publishers = subscriber.getPublishers();
        for (User publisher : publishers) {
            if (publisher.getLogin().equals(publisherName)) {
                return true;
            }
        }
        return false;
    }
}
