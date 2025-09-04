package com.example.configuration;

import com.example.security.CustomAuthenticationFailureHandler;
import com.example.security.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login/**", "/public/**",  "/error", "/webjars/**", "/login/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .oauth2Login(oauth2 -> oauth2
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .successHandler(new CustomAuthenticationSuccessHandler())
                );
        return http.build();
    }
}
