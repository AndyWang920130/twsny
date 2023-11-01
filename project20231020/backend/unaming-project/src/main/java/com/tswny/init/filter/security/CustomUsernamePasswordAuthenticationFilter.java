package com.tswny.init.filter.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


//    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, String url, HttpMethod httpMethod) {
//        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
//        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(path, httpMethod.name()
//
//    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return super.attemptAuthentication(request, response);
    }
}
