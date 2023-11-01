package com.tswny.init.web.rest;

import com.tswny.init.web.rest.vm.LoginForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/authenticate")
public class UserAuthenticationResource {

    @PostMapping("/username")
    public String authenticate(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        return "1111";
    }
}
