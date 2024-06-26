package com.example.keephamapi.security.jwt;

import com.example.keephamapi.common.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@PropertySource("classpath:application-jwt.yml")
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenProvider {

    @Value("${secret}")
    private String jwtSecret;

    @Value("${expiration}")
    private int jwtExpiration;

    @Value("${refresh}")
    private int jwtRefreshExpiration;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    public String generateToken(String loginId) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime accessTokenExpire = now.plusMinutes(jwtExpiration);

        String accessToken =  Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(accessTokenExpire.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return accessToken;
    }

    public String generateRefreshToken(String loginId) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime refreshTokenExpire = now.plusDays(jwtRefreshExpiration);

        String refreshToken =  Jwts.builder()
                .setSubject(loginId)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(refreshTokenExpire.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return refreshToken;
    }

    public String getUserIdFromJwt(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw e;
        }
    }
}
