package be.gchatbe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "friend_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequest {
    @Id
    private UUID id;

    private String senderId;
    private String receiverId;

    private FriendRequestStatus status;
    private Instant createdAt;
}
