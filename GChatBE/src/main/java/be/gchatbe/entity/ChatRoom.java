package be.gchatbe.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Document(collection = "chat_rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {
    @Id
    private UUID id;
    private String name;
    private String displayName;

    private List<String> memberIds;
    private String adminId;
    private boolean isGroup;
    private Instant createdAt;
}
