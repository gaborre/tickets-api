package com.gabn.tickets.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.gabn.tickets.constants.JwtConstants.BEARER_START_STRING;
import static com.gabn.tickets.constants.JwtConstants.VALIDITY_TIME_IN_MILLISECONDS;

@Slf4j
@Component
public class JwtUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = 617922316636859967L;

    private final String jwtSecret;

    public JwtUtil(@Value("${jwt-secret}") String jwtSecret) {
        this.jwtSecret = "de10f8c9-cdaf-4bba-8e09-d55361429e27";
    }

    public Object getClaimByKeyFromToken(String token, String key) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(key);
    }

    public Object getClaimByKeyFromToken(String token, String key, Class<?> clazz) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get(key, clazz);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
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
        log.info("jwt: {}", token);
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

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return !expiration.before(new Date());
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

    public Boolean canTokenBeRefreshed(String token) {
        return isTokenExpired(token);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    public Boolean validateToken(String token, String userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails) && isTokenExpired(token);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /*public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil("de10f8c9-cdaf-4bba-8e09-d55361429e27");
        Map<String, Object> claims = Map.of("type", "tickets");
        String jwt = jwtUtil.doGenerateToken(claims, "german", "admin");
        log.info("jwt: {}", jwt);
    }*/
}
