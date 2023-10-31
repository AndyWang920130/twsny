package com.tswny.init.web.rest;

import com.tswny.init.service.UserService;
import com.tswny.init.service.dto.UserDTO;
import com.tswny.init.web.rest.vm.UserVM;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO create(@RequestBody(required = true) UserVM userVM) {
        return userService.createUser(userVM);
    }
}
