package com.kpi.recipes.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kpi.recipes.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    private Algorithm algorithm;
    private static final String USERNAME_KEY =  "USERNAME";
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostConstruct
    public void postConstructor(){
        algorithm =  Algorithm.HMAC256(algorithmKey);
    }
    public String generateJWT(User user){
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }
    public String getUsername(String token){
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }
    public void addToBlacklist(String token) {
        tokenBlacklistService.addToBlacklist(token);
    }

    public boolean isBlacklisted(String token) {
        return tokenBlacklistService.isBlacklisted(token);
    }
}
