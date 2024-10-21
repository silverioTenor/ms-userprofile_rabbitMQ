package com.ms.userprofile.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ms.userprofile.models.UserDetailsImplModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    @Value("${token.jwt.secret}")
    private String secret_key;

    @Value("${token.jwt.issuer}")
    private String issuer;

    @Value("${token.jwt.expiration}")
    private Integer expirationTime;

    @Value("${timezone}")
    private String timezone;

    public String generateToken(UserDetailsImplModel user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret_key);
            return JWT.create()
                    .withIssuer(issuer)
                    .withIssuedAt(getDateTime())
                    .withExpiresAt(getExpirationTime())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Generate token failed: ", e);
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret_key);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token invalid or expired!");
        }
    }

    private Instant getDateTime() {
        return ZonedDateTime.now(ZoneId.of(timezone)).plusHours(expirationTime).toInstant();
    }

    private Instant getExpirationTime() {
        return ZonedDateTime.now(ZoneId.of(timezone)).toInstant();
    }
}
