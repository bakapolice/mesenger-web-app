package com.example.messenger.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.messenger.entity.RefreshTokenEntity;
import com.example.messenger.entity.UserEntity;
import com.example.messenger.exception.TokenRefreshException;
import com.example.messenger.exception.UserNotFoundException;
import com.example.messenger.mapper.UserToEntityMapper;
import com.example.messenger.models.RefreshToken;
import com.example.messenger.repository.RefreshTokensRepository;
import com.example.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${auth.jwt.refresh.expirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokensRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final UserToEntityMapper userMapper;

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity createRefreshToken(Long userId) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        refreshToken.setUser(userEntity);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("Not found!"));
        return refreshTokenRepository.deleteByUser(user);
    }
}
