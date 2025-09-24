package be.gchatbe.controller;


import be.gchatbe.entity.User;
import be.gchatbe.service.JwtService;
import be.gchatbe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Mono<User> register(@RequestBody User user) {
        user.setId(UUID.randomUUID());
        user.encodePassword(passwordEncoder);
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestParam String username, @RequestParam String password) {
        return userService.getByUsername(username)
                .flatMap(user -> userService.checkPassword(user, password)
                        .flatMap(valid -> {
                            if (valid) {
                                return jwtService.generateToken(user.getUsername(), user.getRole());
                            } else {
                                return Mono.error(new RuntimeException("Invalid credentials"));
                            }
                        })
                )
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }
}
