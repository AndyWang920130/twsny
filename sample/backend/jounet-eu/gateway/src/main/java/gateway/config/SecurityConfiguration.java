package gateway.config;

import gateway.security.manager.CustomAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;
    // 鉴权白名单（这些路径不做鉴权）
    private static final String[] WHITE_ARRAY = new String[] {
            "/api/v1/authenticate",  // 登录授权接口
            "/api/v1/register",   // 注册接口
            "/health",            // 健康检查
            "/favicon.ico",        // 静态资源
            "/service/auth/login",  // 原始请求
            "/login", // rewritePath 后路径
            "/service/business/foo/exception",  // 原始请求
            "/foo/exception" // rewritePath 后路径
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // 关闭 CSRF
                .authorizeExchange(exchanges -> exchanges
                        // ✅ 放行认证接口
                        .pathMatchers(WHITE_ARRAY).permitAll()
                        // ✅ 其他路径需要认证
                        .anyExchange().authenticated()
                )
                // ✅ 启用 JWT 鉴权（资源服务器模式）
//                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
                .authenticationManager(customAuthenticationManager)
                .build();
    }
}
