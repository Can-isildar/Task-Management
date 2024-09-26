package com.task_management.service.impl;

import com.task_management.service.AuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String SECRET_KEY = "secret_key";

    @Override
    public String generateToken(UserDetails userDetails) {
        long expirationTime = 1000 * 60 * 60 * 10; // 10 saat geçerli
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}

