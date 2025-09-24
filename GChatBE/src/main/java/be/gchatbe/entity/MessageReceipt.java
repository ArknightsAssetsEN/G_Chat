package be.gchatbe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "message_receipts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageReceipt {
    @Id
    private UUID id;

    private String messageId;
    private String userId;

    private ReceiptStatus status;
    private Instant updatedAt;
}
