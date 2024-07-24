package com.chatop.portal.services;


import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {

    private SecretKey key;
    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    public JWTUtils() {
        String secretString = "Dds5bCeJXNQCxZruYn7psw1Q23y9pQnztRDAvCNIJQA=";
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretString);
            this.key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to initialize JWTUtils", e);
        }

    }

    public String generateToken(UserDetails userDetails) {
        String token =  Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact();

            System.out.println("Generated Token: " + token);
            return token;
    }
    
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        String token = Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact();

            System.out.println("Generated Token: " + token);
            return token;
    }

    public String extractUsername(String token) {
        System.out.println("Extracting Username from Token: " + token);
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsFunction) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
                
        System.out.println("Extracted Claims: " + claims);
        return claimsFunction.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

}
