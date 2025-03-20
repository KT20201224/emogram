package com.emogram.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private Key key;  // 키를 Key 객체로 저장
    /**
     * SecretKey 초기화 (강력한 키로 변환)
     */
    @PostConstruct
    protected void init() {
        try {
            // Base64로 디코딩 시 문제가 발생할 경우 예외를 던지도록 함
            byte[] decodedKey = Base64.getDecoder().decode(secretKey);
            key = Keys.hmacShaKeyFor(decodedKey);  // 강력한 키 생성
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid JWT secret key. Make sure it is a valid Base64 encoded string.", e);
        }
    }

    /**
     * JWT 토큰 생성
     */
    public String createToken(String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)  // 키 객체를 사용하여 서명
                .compact();
    }

    /**
     * JWT 토큰에서 이메일 추출
     */
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * JWT 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }
}