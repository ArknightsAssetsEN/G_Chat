package be.gchatbe.controller;

import be.gchatbe.dto.ApiResponseDto;
import be.gchatbe.dto.ApiStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@AllArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {
    // Public endpoint, ai cũng truy cập được
    @GetMapping("/hello")
    public Mono<ApiResponseDto<String>> publicHello(ServerWebExchange exchange) {
        return Mono.just(
                ApiResponseDto.<String>builder()
                        .code(200)
                        .status(ApiStatus.SUCCESS.getValue())
                        .message("Public endpoint reached")
                        .data("Hello from public endpoint 👋")
                        .timestamp(Instant.now())
                        .path(exchange.getRequest().getURI().getPath())
                        .build()
        );
    }
}
