package ru.ivanov.creditservice.security;

import lombok.extern.slf4j.Slf4j;
import ru.ivanov.creditservice.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String extractUserId(String token) {
        try {
            log.info("Извлечение userId из токена: {}", token);
            return getClaims(token).getSubject();
        } catch (Exception e) {
            log.error("Ошибка при извлечении userId из токена: {}", e.getMessage());
            throw new InvalidJwtException("Невалидный JWT токен", e);
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}