package com.example.orderservice.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenValidator {
    @Autowired
    private SecretKey secretKey;

    public long validateAndGetUserId(String token) {
        if(token == null || !token.startsWith("Bearer ")){
            throw new RuntimeException("Invalid token");
        }
        token = token.substring(7);
        Claims claims;
        try{
           //claims=Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
           claims=Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException ex){
            throw ex;
        }catch(Exception e){
            throw new RuntimeException("Invalid token");
        }
        return Long.parseLong(claims.getSubject());
    }
}
