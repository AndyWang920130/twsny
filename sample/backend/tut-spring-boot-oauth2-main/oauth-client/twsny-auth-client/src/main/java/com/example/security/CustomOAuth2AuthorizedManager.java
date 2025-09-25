package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2AuthorizedManager {
    @Autowired
    private OAuth2AuthorizedClientManager authorizedClientManager;

    public String callDeviceApi() {
        OAuth2AuthorizeRequest authorizeRequest =
                OAuth2AuthorizeRequest.withClientRegistrationId("twsny-auth3")
                        .principal("twsny-auth3-principal") // 这里是一个逻辑名
                        .build();

        OAuth2AuthorizedClient authorizedClient =
                authorizedClientManager.authorize(authorizeRequest);

        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        // 用 access_token 调用受保护的 API
        System.out.println("Got Access Token: " + accessToken);

        return accessToken;
    }
}
