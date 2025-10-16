package gateway.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@EnableConfigurationProperties(UriConfiguration.class)
@Configuration
public class RouterConfiguration {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String httpUri = uriConfiguration.getHttpbin();
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri))
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        return builder.routes()
                .route("path_route", r -> r.path("/get")
                        .uri("https://httpbin.org"))
                .route("host_route", r -> r.host("*.myhost.org")
                        .uri("https://httpbin.org"))
                .route("rewrite_route", r -> r.host("*.rewrite.org")
                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
                        .uri("https://httpbin.org"))
                .route("path_route", r -> r.path("/service/business/**")
                        .filters(f -> f
                                .stripPrefix(1) //去掉第一个路径前缀
                                .rewritePath("/business/(?<segment>.*)", "/${segment}")
                                .circuitBreaker(c -> c.setName("business").setFallbackUri("forward:/fallback").addStatusCode("500"))
                        )
                        .uri(uriConfiguration.getBusiness()))
                .route("circuit_breaker_route", r -> r.host("*.circuitbreaker.org")
                        .filters(f -> f.circuitBreaker(c -> c.setName("slowcmd")))
                        .uri("https://httpbin.org"))
                .route("circuit_breaker_fallback_route", r -> r.host("*.circuitbreakerfallback.org")
                        .filters(f -> f.circuitBreaker(c -> c.setName("business").setFallbackUri("forward:/fallback")))
                        .uri("https://httpbin.org"))
                .build();
    }
}
