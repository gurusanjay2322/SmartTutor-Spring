package com.example.SmartTutor.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // ✅ Generate token - you can still call this normally
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // example: "ADMIN", "PARENT", "STUDENT"
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key)
                .compact();
    }

    // ✅ Extract username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ Extract role — handles both "role" and "admin: true"
    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);

        // If role field exists, return it
        if (claims.containsKey("role")) {
            return claims.get("role", String.class);
        }

        // Otherwise, check if there's an admin flag
        if (Boolean.TRUE.equals(claims.get("admin", Boolean.class))) {
            return "ADMIN";
        }

        // Default fallback (if neither found)
        return "STUDENT";
    }

    // ✅ Utility method to parse and return all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
