package com.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;

@Configuration
public class OAuth2ClientConfiguration {
    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository) {
//        ClientRegistration clientRegistration =clientRegistrationRepository.findByRegistrationId("twsny-auth3");
//        return new DefaultOAuth2AuthorizedClientManager(
//                clientRegistration,
//                new AuthenticatedPrincipalOAuth2AuthorizedClientRepository()
//        );

        // 保存授权的客户端信息（默认存储在内存里）
        OAuth2AuthorizedClientService authorizedClientService =
                new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);

        // 也可以用 web repository（如果有用户上下文）
        AuthenticatedPrincipalOAuth2AuthorizedClientRepository authorizedClientRepository =
                new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);

        // 构建默认的 ClientManager
//        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
//                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);

        // 非 Web 上下文专用 manager
        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService
                );

        // 配置 client_credentials 支持
        authorizedClientManager.setAuthorizedClientProvider(
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build()
        );

        return authorizedClientManager;
    }
}
