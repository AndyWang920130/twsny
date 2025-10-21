package com.example.configuration;

import com.example.filter.RemoveDpopFilter;
import com.example.security.CustomAuthenticationFailureHandler;
import com.example.security.CustomAuthenticationSuccessHandler;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .oidc(Customizer.withDefaults())	// Enable OpenID Connect 1.0
//                                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.authenticationProviders(configureAuthenticationProviders()))
//                                .tokenEndpoint(tokenEndpoint -> tokenEndpoint.authenticationProviders(configureTokenAuthenticationProviders()))

                )
                .authorizeHttpRequests((authorize) ->
                        authorize
//                                .requestMatchers("/oauth2/token").permitAll() // 允许 token endpoint
                                .anyRequest().authenticated()
                )
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .cors(Customizer.withDefaults())
//                .csrf(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) //
                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
//                 .formLogin(Customizer.withDefaults())
                 .formLogin(form -> form
//                 .loginPage("/none") // 指定一个不存在的页面
//                 .loginProcessingUrl("/login") // POST /login 仍然可以认证
                 .successHandler(new CustomAuthenticationSuccessHandler())
                 .failureHandler(new CustomAuthenticationFailureHandler()));
                 // .permitAll()); // 允许匿名访问 POST /login

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails1 = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password")
                .roles("USER")
                .build();
        UserDetails userDetails2 = User.withDefaultPasswordEncoder()
                .username("user2")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient authorizationCodeTwsnyClient1 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("twsny-client")
                .clientSecret("{noop}123456")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8081/login/oauth2/code/twsny-auth")
                .postLogoutRedirectUri("http://localhost:8081/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.EMAIL)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        RegisteredClient authorizationCodeTwsnyClient2 = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("twsny-client2")
                .clientSecret("{noop}12345678")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8081/login/oauth2/code/twsny-auth2")
                .postLogoutRedirectUri("http://localhost:8081/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.EMAIL)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        RegisteredClient clientCredentialsTwsnyClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("twsny-credentials-client")
                .clientSecret("{noop}123456789")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) // 客户端模式
                .scope("device.read")
                .scope("device.write")
                .build();

        return new InMemoryRegisteredClientRepository(authorizationCodeTwsnyClient1, authorizationCodeTwsnyClient2, clientCredentialsTwsnyClient);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    private Consumer<List<AuthenticationProvider>> configureAuthenticationProviders() {
        return providers -> {
            // 移除或替换 DPoP 相关的认证提供者
            providers.removeIf(provider ->
                    provider.getClass().getName().contains("OAuth2AuthorizationCodeRequestAuthenticationProvider") ||
                            provider.getClass().getName().contains("DPoP"));

            // 或者添加自定义的认证提供者
            // providers.add(0, new CustomAuthorizationCodeAuthenticationProvider());
        };
    }

    private Consumer<List<AuthenticationProvider>> configureTokenAuthenticationProviders() {
        return providers -> {
            // 移除 token endpoint 中的 DPoP 相关认证提供者
            providers.removeIf(provider ->
                    provider.getClass().getName().contains("DPoP"));
        };
    }

}
