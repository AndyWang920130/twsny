package com.example.rest;

import com.example.rest.vm.LoginVM;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AuthResource {
    @Value("${twsny.login.callback.path}")
    private String twsnyLoginCallbackPath;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody LoginVM loginVM) throws IOException {
        String username = loginVM.getUsername();
        String password = loginVM.getPassword();

        String token = username;
        return ResponseEntity.ok(token);
    }

    @GetMapping("/oauth2/authenticate/callback")
    public ResponseEntity<String> user(@AuthenticationPrincipal OAuth2User oauthUser,
                                       HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clientUsername = oauthUser.getAttribute("preferred_username");
        String username = oauthUser.getAttribute("name");
        String email = oauthUser.getAttribute("email");

        String token = username;
        response.sendRedirect(twsnyLoginCallbackPath + "?token=" + token);
        return ResponseEntity.ok(token);
    }

}
