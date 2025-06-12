package com.example.coffeeshoprestapi.auth;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "mySecretKey";
    private static final long EXPIRATION = 86400000; // 1 day

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("CoffeeShopAPI")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("Valid token for user: " + claims.getSubject());
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            System.err.println("Token expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Invalid token: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Validation error: " + e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }
}
