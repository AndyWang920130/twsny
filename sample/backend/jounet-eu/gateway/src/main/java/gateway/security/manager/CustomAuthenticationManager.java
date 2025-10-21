package gateway.security.manager;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = (String) authentication.getCredentials();
        if (isValidToken(token)) {
            return Mono.just(new UsernamePasswordAuthenticationToken("user", null, List.of()));
        } else {
            return Mono.empty();
        }
    }

    private boolean isValidToken(String token) {
        // 自定义验证逻辑，比如解析JWT或查Redis
        return token.startsWith("Bearer ");
    }
}
