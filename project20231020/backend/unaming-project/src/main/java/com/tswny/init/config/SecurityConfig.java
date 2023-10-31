package com.tswny.init.config;

import com.tswny.init.filter.security.CustomTenantFilter;
import com.tswny.init.service.CustomUserDetailService;
import com.tswny.init.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/api/v1/users/**").permitAll()
                        .antMatchers("/api/v1/websites/**").permitAll()
                        .antMatchers("/api/v1/persons/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 添加自定义过滤器
                .addFilterBefore(new CustomTenantFilter(), AuthorizationFilter.class)
                //默认的HTTP Basic Auth认证
                // .httpBasic(Customizer.withDefaults())
                //默认的表单登录
                .formLogin(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
//        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
//        users.createUser(User.withUsername("user").password("{noop}user").roles("USER").build());
//        users.createUser(User.withUsername("admin").password("{noop}admin").roles("USER", "ADMIN").build());
//        return users;
        return new CustomUserDetailService(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
