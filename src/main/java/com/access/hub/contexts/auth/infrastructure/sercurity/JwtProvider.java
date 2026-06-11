package com.access.hub.contexts.auth.infrastructure.sercurity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final long expirationInMs;

    // Spring Boot 3.5.x tự động map config mượt mà
    public JwtProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationInMs) {
        // Khởi tạo SecretKey chuẩn hóa bảo mật
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationInMs = expirationInMs;
    }

    public String generateAccessToken(String username, String email, String deptCode, String deptName, String projectCode) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(username)
                .claim("email", email)
                .claim("deptCode", deptCode)
                .claim("deptName", deptName)
                .claim("projectCode", projectCode)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationInMs))
                .signWith(secretKey)
                .compact();
    }
}
