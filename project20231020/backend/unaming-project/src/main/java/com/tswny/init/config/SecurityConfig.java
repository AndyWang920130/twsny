package com.tswny.init.config;

import com.tswny.init.filter.security.CustomTenantFilter;
import com.tswny.init.filter.security.CustomUsernamePasswordAuthenticationFilter;
import com.tswny.init.filter.security.JwtFilter;
import com.tswny.init.service.CustomUserDetailService;
import com.tswny.init.service.UserService;
import com.tswny.init.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.servlet.ServletOutputStream;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final String loginProcessingUrl = "/api/v1/authenticate/username";
    private final UserService userService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final SecurityProblemSupport securityProblemSupport;



    public SecurityConfig(UserService userService, AuthenticationConfiguration authenticationConfiguration, SecurityProblemSupport securityProblemSupport) {
        this.userService = userService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.securityProblemSupport = securityProblemSupport;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                 // 添加用户民密码过滤器
                // .addFilterBefore(initCustomUsernamePasswordAuthenticationFilter("/api/v1/authenticate/username", HttpMethod.POST), UsernamePasswordAuthenticationFilter.class)
                 // 添加自定义过滤器
                .addFilterBefore(new CustomTenantFilter(), AuthorizationFilter.class)
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                    .authenticationEntryPoint(securityProblemSupport)
                    .accessDeniedHandler(securityProblemSupport)
                .and()
                    .headers()
                    .contentSecurityPolicy("default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline';")
                .and()
                    .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                    .frameOptions()
                    .deny()
                .and()
                    .authorizeHttpRequests(authorize -> authorize
                            // .antMatchers("/api/v1/authenticate/username").permitAll()
                            .antMatchers("/api/v1/users/**").permitAll()
                            .antMatchers("/api/v1/websites/**").permitAll()
                            .antMatchers("/api/v1/clothes/**").permitAll()
                            .antMatchers("/api/v1/upload/common/**").permitAll()
                            // .antMatchers("/api/v1/persons/**").hasRole("USER")
                            .antMatchers("/api/v1/**").authenticated()
                    )
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    //默认的HTTP Basic Auth认证
                    .httpBasic(Customizer.withDefaults())
                    //默认的表单登录
                    // .formLogin(Customizer.withDefaults())
                    .formLogin()
                    // 触发登录校验
                    .loginProcessingUrl(loginProcessingUrl)
                    .successHandler(successHandler());
//                .failureHandler()
                // 成功后跳转页面
                // .defaultSuccessUrl("/index.html");
        return http.build();
    }


    private CustomUsernamePasswordAuthenticationFilter initCustomUsernamePasswordAuthenticationFilter(String path, HttpMethod httpMethod) {
        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new CustomUsernamePasswordAuthenticationFilter();
        customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customUsernamePasswordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(path, httpMethod.name()));

        return customUsernamePasswordAuthenticationFilter;
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


    @Bean
    public AuthenticationManager authenticationManager(){
        AuthenticationManager authenticationManager = null;
        try {
            authenticationManager = authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            log.error("authenticationManager init failure");
        }
        return authenticationManager;
    }


    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            response.setContentType("application/json;charset=UTF-8");
            //登录成功后获取当前登录用户
//            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
//            UserDetail userDetail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            log.info("用户[{}]于[{}]登录成功!", userDetail.getUser().getUsername(), new Date());
//            WriteResponse.write(httpServletResponse, new SuccessResponse());
            String token = JwtTokenUtil.getInstance().createToken(authentication, false);
            // String result = JSON.toJSONString(loginResult, SerializerFeature.DisableCircularReferenceDetect);

            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(token.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        };
    }

    @Bean
    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
//            log.debug("Registering CORS filter");
//            source.registerCorsConfiguration("/api/**", config);
//            source.registerCorsConfiguration("/management/**", config);
//        }
//        return new CorsFilter(source);


        //1. 添加 CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些原始域
        config.addAllowedOrigin("*");
        //是否发送 Cookie
        // config.setAllowCredentials(true);
        //放行哪些请求方式
        config.addAllowedMethod("*");
        //放行哪些原始请求头部信息
        config.addAllowedHeader("*");
        //暴露哪些头部信息
        config.addExposedHeader("*");

        //2. 添加映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        corsConfigurationSource.registerCorsConfiguration("/management/**", config);
//        corsConfigurationSource.registerCorsConfiguration("/api/**",config);
//        corsConfigurationSource.registerCorsConfiguration("/management/**", config);

        return new CorsFilter(corsConfigurationSource);
    }

}
