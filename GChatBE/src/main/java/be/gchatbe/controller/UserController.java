package be.gchatbe.controller;

import be.gchatbe.dto.ApiResponseDto;
import be.gchatbe.entity.User;
import be.gchatbe.repository.UserRepository;
import be.gchatbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
    public Flux<User> findAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/profile/{username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/{id}")
    public Mono<User> getById(@RequestParam UUID id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public Mono<User> save(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public Mono<User> deleteById(@RequestParam UUID id) {
        return userService.deleteUser(id);
    }
}
