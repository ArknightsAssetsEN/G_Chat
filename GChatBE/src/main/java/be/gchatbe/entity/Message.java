package be.gchatbe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    private UUID id;

    private String roomId;
    private String senderId;

    private MessageType type;
    private String content;
    private String fileUrl;

    private Instant sentAt;
    private boolean edited;
    private boolean deleted;
}
