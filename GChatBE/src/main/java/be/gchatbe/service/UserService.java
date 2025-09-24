package be.gchatbe.service;

import be.gchatbe.entity.User;
import be.gchatbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Mono<User> deleteUser(UUID id) {
        return userRepository.findById(id);
    }

    public Mono<User> getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public Mono<Boolean> checkPassword(User user, String rawPassword) {
        return Mono.just(encoder.matches(rawPassword, user.getPassword()));
    }


}
