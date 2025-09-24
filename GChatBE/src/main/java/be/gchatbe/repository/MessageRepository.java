package be.gchatbe.repository;

import be.gchatbe.entity.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface MessageRepository extends ReactiveMongoRepository<Message, UUID> {
}
