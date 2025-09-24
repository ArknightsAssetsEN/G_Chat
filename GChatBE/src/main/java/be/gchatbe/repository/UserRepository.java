package be.gchatbe.repository;

import be.gchatbe.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveMongoRepository<User, UUID> {
    Mono<User> findByUsername(String username);
    Mono<Boolean> existsByUsername(String username);
}
