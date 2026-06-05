package com.auth.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes());

    }

    public String generateToken(
            String correo) {

        Date now = new Date();

        Date expiry =
                new Date(
                        now.getTime()
                                + expiration);

        return Jwts.builder()
                .subject(correo)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(
                        getSigningKey())
                .compact();
    }
    
    
    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    public boolean isTokenValid(
            String token,
            String correo) {

        String username =
                extractUsername(token);

        return username.equals(correo);
    }
}