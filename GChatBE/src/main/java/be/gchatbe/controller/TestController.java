package be.gchatbe.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {
    // Public endpoint, ai cũng truy cập được
    @GetMapping("/hello")
    public Mono<String> publicHello() {
        return Mono.just("Hello from public endpoint 👋");
    }
}
