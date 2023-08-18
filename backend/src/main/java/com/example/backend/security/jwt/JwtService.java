package com.example.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    @Value(value = "password")
    private String secret;

    public String createToken(String userCode) {
        return Jwts.builder()
                .setSubject(userCode)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ TimeUnit.HOURS.toMillis(14)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    public Claims validateToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
