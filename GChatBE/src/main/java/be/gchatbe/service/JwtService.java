package be.gchatbe.service;

import be.gchatbe.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String jwtSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.expiration}")
    private int jwtExpirationInSeconds;

    private SecretKey getSigningKey() {
        return new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
//        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtExpirationInSeconds);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getUsername())
                .claim("roles", user.getAuthorities().stream()
                        .map(Object::toString)
                        .toList())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtExpirationInSeconds * 24 * 7); // 7 days

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getUsername())
                .claim("type", "refresh")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(getSigningKey())
                .compact();
    }
}
