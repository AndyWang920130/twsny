package gateway.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;

@RestController
public class FooResource {
    @RequestMapping("/foo")
    public Mono<String> foo() {
        return Mono.just("foo");
    }

    @RequestMapping("/foo/exception")
    public Mono<String> exception() {
        return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Simulate failure"));
    }
}
