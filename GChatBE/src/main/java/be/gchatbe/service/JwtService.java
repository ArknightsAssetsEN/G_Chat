package be.gchatbe.service;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "your-super-secret-key-must-be-at-least-256-bits-long".getBytes()
    );

    public Mono<String> generateToken(String username, String role) {
        Instant now = Instant.now();
        String token = Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(3600)))
                .signWith(secretKey)
                .compact();
        return Mono.just(token);
    }

    public Mono<String> validateAndGetUsername(String token) {
        try {
            String subject = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return Mono.just(subject);
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}
