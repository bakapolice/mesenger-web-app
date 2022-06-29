package com.example.messenger.security.service.old;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class DefaultTokenService implements TokenService {

    Logger logger = LoggerFactory.getLogger(DefaultTokenService.class);

    @Value("${auth.jwt.secret.access}")
    private String accessSecretKey;
    @Value("${auth.jwt.secret.refresh}")
    private String refreshSecretKey;

    @Override
    public String generateAccessToken(String userId, String userLogin) {
        Algorithm algorithm = Algorithm.HMAC256(accessSecretKey);

        Instant now = Instant.now();
        Instant exp = now.plus(5, ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("auth-service") // iss(Issuer) — издатель токена
                .withAudience("messenger") // aud(Audience) — для какого сервиса предназначается токен
                .withClaim("login", userLogin)
                .withSubject(userId) // sub(Subject) — идентификатор клиента (userId)
                .withIssuedAt(Date.from(now)) // текущее время формирования токена
                .withExpiresAt(Date.from(exp)) //  вычисленное время окончания действия токена (выдаем на 5 минут)
                .sign(algorithm);
    }

    @Override
    public String generateRefreshToken(String userId) {
        Algorithm algorithm = Algorithm.HMAC256(refreshSecretKey);

        Instant now = Instant.now();
        Instant exp = now.plus(30, ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("auth-service") // iss(Issuer) — издатель токена
                .withAudience("messenger") // aud(Audience) — для какого сервиса предназначается токен
                .withSubject(userId) // sub(Subject) — идентификатор клиента (userId)
                .withIssuedAt(Date.from(now)) // текущее время формирования токена
                .withExpiresAt(Date.from(exp)) //  вычисленное время окончания действия токена (выдаем на 5 минут)
                .sign(algorithm);
    }

    @Override
    public boolean checkToken(String token, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            if (!decodedJWT.getIssuer().equals("auth-service")) {
                logger.error("Issuer is incorrect!");
                return false;
            }

            if (!decodedJWT.getAudience().contains("messenger")) {
                logger.error("Audience is incorrect");
                return false;
            }

        } catch (JWTVerificationException e) {
            logger.error("Token is invalid: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return checkToken(accessToken, accessSecretKey);
    }

    @Override
    public boolean checkRefreshToken(String refreshToken) {
        return checkToken(refreshToken, refreshSecretKey);
    }

    @Override
    public String getUserIdFromToken(String token, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    @Override
    public String getUserIdFromAccessToken(String token) {
        return getUserIdFromToken(token, accessSecretKey);
    }

    @Override
    public String getUserIdFromRefreshToken(String token) {
        return getUserIdFromToken(token, refreshSecretKey);
    }


}
