package gateway.security.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * be replaced by CustomAuthenticationManager {@link gateway.security.manager.CustomAuthenticationManager}
 */
@Deprecated
//@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    // 鉴权白名单（这些路径不做鉴权）
    private static final List<String> WHITE_LIST = List.of(
            "/api/v1/authorize",  // 登录授权接口
            "/api/v1/register",   // 注册接口（可选）
            "/health",            // 健康检查
            "/favicon.ico"        // 静态资源
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        //  如果路径在白名单中，直接放行
        if (WHITE_LIST.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }
        // 例：从请求头取 Token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        // 简单判断是否携带 token
        if (token == null || !isValidToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 鉴权通过，继续路由转发
        return chain.filter(exchange);
    }

    private boolean isValidToken(String token) {
        // TODO: 校验JWT或调用Auth服务
        return token.startsWith("Bearer ");
    }

    @Override
    public int getOrder() {
        // 优先级越小越先执行
        return -1;
    }
}
