package be.gchatbe.repository;

import be.gchatbe.entity.ChatRoom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface ChatRoomRepository extends ReactiveMongoRepository<ChatRoom, UUID> {
}

