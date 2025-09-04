package com.example.rest;

import com.example.rest.vm.LoginVM;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
                                       @AuthenticationPrincipal OidcUser oidcUser,
                                       HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String clientUsername = oauthUser.getAttribute("preferred_username");
//        String username = oauthUser.getAttribute("name");
//        String email = oauthUser.getAttribute("email");
        String name = oidcUser.getName();
        String username = oidcUser.getPreferredUsername();
        String fullName  = oidcUser.getFullName();
        String email = oidcUser.getEmail();
        String phoneNumber =  oidcUser.getPhoneNumber();
        System.out.println(name + ", " + username + ", " + fullName + ", " + email + ", " + phoneNumber);

        String token = name;
        response.sendRedirect(twsnyLoginCallbackPath + "?token=" + token);
        return ResponseEntity.ok(token);
    }

}
