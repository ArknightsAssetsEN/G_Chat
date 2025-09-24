package be.gchatbe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Collection;
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

    private String username;
    private String password;
    private String displayName;
    private String role;

    private String avatarUrl;
    private Instant lastActive;
    private List<String> friendIds;
    private String statusMessage;
    private boolean online;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        if (this.password != null && !this.password.startsWith("$2a$")) {
            // Nếu chưa encode thì encode
            this.password = passwordEncoder.encode(this.password);
        }
    }

}
