package be.gchatbe.controller;


import be.gchatbe.dto.ApiResponseDto;
import be.gchatbe.dto.ApiStatus;
import be.gchatbe.entity.User;
import be.gchatbe.service.JwtService;
import be.gchatbe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Mono<ApiResponseDto<User>> register(
            @RequestBody User user,
            ServerWebExchange exchange) {

        // tạo UUID và encode password
        user.setId(UUID.randomUUID());
        user.encodePassword(passwordEncoder);

        return userService.getByUsername(user.getUsername())
                .flatMap(existingUser -> Mono.just(
                        ApiResponseDto.<User>builder()
                                .code(400)
                                .status(ApiStatus.FAIL.getValue())
                                .message("Username already exists")
                                .timestamp(Instant.now())
                                .path(exchange.getRequest().getURI().getPath())
                                .build()

                ))
                .switchIfEmpty(
                        userService.createUser(user)
                                .map(savedUser -> ApiResponseDto.<User>builder()
                                        .code(201)
                                        .status(ApiStatus.SUCCESS.getValue())
                                        .message("User registered successfully")
                                        .data(savedUser)
                                        .timestamp(Instant.now())
                                        .path(exchange.getRequest().getURI().getPath())
                                        .build()
                                )                                )
                                .onErrorResume(e -> Mono.just(ApiResponseDto.<User>builder()
                                            .code(400)
                                            .status(ApiStatus.FAIL.getValue())
                                            .message(e.getMessage())
                                            .timestamp(Instant.now())
                                            .path(exchange.getRequest().getURI().getPath())
                                            .build()

                                )
                );
    }

    @PostMapping("/login")
    public Mono<ApiResponseDto<String>> login(
            @RequestParam String username,
            @RequestParam String password,
            ServerWebExchange exchange) {

        return userService.getByUsername(username)
                .flatMap(user -> userService.checkPassword(user, password)
                        .flatMap(valid -> {
                            if (valid) {
                                return jwtService.generateToken(user.getUsername(), user.getRole().name())
                                        .map(token -> ApiResponseDto.<String>builder()
                                                .code(200)
                                                .status(ApiStatus.SUCCESS.getValue())
                                                .message("Login successful")
                                                .data(token)
                                                .timestamp(Instant.now())
                                                .path(exchange.getRequest().getURI().getPath())
                                                .build()
                                        );
                            } else {
                                return Mono.just(ApiResponseDto.<String>builder()
                                        .code(401)
                                        .status(ApiStatus.FAIL.getValue())
                                        .message("Invalid credentials")
                                        .timestamp(Instant.now())
                                        .path(exchange.getRequest().getURI().getPath())
                                        .build());
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ApiResponseDto.<String>builder()
                        .code(404)
                        .status(ApiStatus.ERROR.getValue())
                        .message("User not found")
                        .timestamp(Instant.now())
                        .path(exchange.getRequest().getURI().getPath())
                        .build()));
    }
}
