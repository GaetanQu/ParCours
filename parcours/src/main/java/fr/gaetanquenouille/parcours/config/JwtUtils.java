package fr.gaetanquenouille.parcours.config;

import java.security.Key;
import java.sql.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // Secret key for JWT
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Expiration time of the token
    private final long expirationTime= 36000000; // 10h

    // Generate a token
    public String generateToken(String username){
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(secretKey)
            .compact();
    }

    // Validate a token
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        }catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }

    // Get the user from a token
    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build().parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
}
