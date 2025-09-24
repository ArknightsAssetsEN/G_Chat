package be.gchatbe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private UUID id;

    @Indexed(unique = true)
    private String username;
    private String password;
    private String displayName;

    private Role role;

    private String avatarUrl;
    private Instant lastActive;
    private boolean online;
    private String statusMessage;

    private List<String> friendIds;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        if (this.password != null && !this.password.startsWith("$2a$")) {
            // Nếu chưa encode thì encode
            this.password = passwordEncoder.encode(this.password);
        }
    }

}
