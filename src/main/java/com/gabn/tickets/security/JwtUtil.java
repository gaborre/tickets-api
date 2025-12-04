package com.gabn.tickets.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.gabn.tickets.constants.JwtConstants.BEARER_START_STRING;
import static com.gabn.tickets.constants.JwtConstants.VALIDITY_TIME_IN_MILLISECONDS;

@Slf4j
@Component
public class JwtUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = 617922316636859967L;

    private final String jwtSecret;

    public JwtUtil(@Value("${jwt-secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts
            .parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean validateToken(final String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return validateTokenStructure(claims) && isValidToken(token);
    }

    public boolean validateTokenStructure(final Claims claims) {
        return (
            Objects.nonNull(claims.get(Claims.AUDIENCE))
                && Objects.nonNull(claims.get(Claims.EXPIRATION))
                && Objects.nonNull(claims.get(Claims.ISSUED_AT))
        );
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired token: {}", e.getMessage());
        } catch (SecurityException e) {
            log.error("Invalid signature{}", e.getMessage());
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
        }
        return false;
    }

    public boolean isJwtToken(final String requestToken) {
        return Objects.nonNull(requestToken) && requestToken.startsWith(BEARER_START_STRING);
    }

    public String doGenerateToken(
        Map<String, Object> claims,
        String subject,
        String audience
    ) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(VALIDITY_TIME_IN_MILLISECONDS);

        return Jwts.builder()
            .claims(claims)
            .audience().add(audience)
            .and()
            .subject(subject)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(getSecretKey(), Jwts.SIG.HS256)
            .compact();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
