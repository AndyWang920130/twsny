package com.tswny.init.web.rest;

import com.tswny.init.service.UserService;
import com.tswny.init.service.dto.UserDTO;
import com.tswny.init.web.rest.vm.UserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {
    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/test")
    public UserDTO createTest(@RequestBody UserVM userVM) {
        log.info("create user: {}", userVM);
        return userService.createUser(userVM);
    }

    @PostMapping
    public UserDTO create(@RequestBody UserVM userVM) {
        log.info("create user: {}", userVM);
        return userService.createUser(userVM);
    }


    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }
}
