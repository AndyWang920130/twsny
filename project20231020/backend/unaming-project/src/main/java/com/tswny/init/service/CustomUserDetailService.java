package com.tswny.init.service;

import com.tswny.init.service.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.findByLogin(username);
        if (userDTO == null) return null;
        String password = userDTO.getPassword();
        // TODO: 2023/10/31 complete user authorities
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        return new User(username, password, grantedAuthorities);
    }
}
