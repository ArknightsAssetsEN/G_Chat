package be.gchatbe;

import be.gchatbe.entity.ChatRoom;
import be.gchatbe.entity.Message;
import be.gchatbe.entity.User;
import be.gchatbe.repository.ChatRoomRepository;
import be.gchatbe.repository.MessageRepository;
import be.gchatbe.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class GChatBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GChatBeApplication.class, args);
    }


//    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo,
                                   ChatRoomRepository chatRepo,
                                   MessageRepository msgRepo) {
        return args -> {

            // Tạo users
            User u1 = User.builder()
                    .id(UUID.randomUUID())
                    .username("alice")
                    .password("123")
                    .displayName("Alice")
                    .online(true)
                    .avatarUrl(null)
                    .lastActive(Instant.now())
                    .friendIds(List.of())
                    .statusMessage("Hi there!")
                    .build();

            User u2 = User.builder()
                    .id(UUID.randomUUID())
                    .username("bob")
                    .password("123")
                    .displayName("Bob")
                    .online(true)
                    .avatarUrl(null)
                    .lastActive(Instant.now())
                    .friendIds(List.of(u1.getUsername()))
                    .statusMessage("Hello!")
                    .build();

            // Tạo chatRoom 1-1
            ChatRoom chat1 = ChatRoom.builder()
                    .id(UUID.randomUUID())
                    .name("Alice & Bob")
                    .memberIds(List.of(u1.getUsername(), u2.getDisplayName()))
                    .isGroup(false)
                    .displayName("")
                    .adminId(u1.getUsername())
                    .createdAt(Instant.now())
                    .build();

            // Tạo messages
            Message m1 = Message.builder()
                    .id(UUID.randomUUID())
                    .chatRoomName("chat1")
                    .senderId(u1.getUsername())
                    .content("Hi Bob!")
                    .timestamp(Instant.now())
                    .read(false)
                    .messageType("text")
                    .build();

            Message m2 = Message.builder()
                    .id(UUID.randomUUID())
                    .chatRoomName("chat1")
                    .senderId(u2.getDisplayName())
                    .content("Hello Alice!")
                    .timestamp(Instant.now())
                    .read(false)
                    .messageType("text")
                    .build();

            // Reactive chaining: delete old data, save new data
            userRepo.deleteAll()
                    .then(chatRepo.deleteAll())
                    .then(msgRepo.deleteAll())
                    .thenMany(userRepo.saveAll(List.of(u1, u2)))
                    .thenMany(chatRepo.save(chat1).flux())
                    .thenMany(msgRepo.saveAll(List.of(m1, m2)))
                    .subscribe(); // subscribe để khởi chạy reactive pipeline
        };
    }


}
