package be.gchatbe.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Document(collection = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    private UUID id;

    private String content;

    private String chatRoomName;
    private String senderId;

    private Instant timestamp;
    private boolean read;
    private String messageType;
}