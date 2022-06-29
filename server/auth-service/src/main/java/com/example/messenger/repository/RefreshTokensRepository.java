package com.example.messenger.repository;

import com.example.messenger.entity.RefreshTokenEntity;
import com.example.messenger.entity.UserEntity;
import com.example.messenger.models.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokensRepository extends CrudRepository<RefreshTokenEntity, String> {
    Optional<RefreshTokenEntity> findByUserId(String userId);
    Optional<RefreshTokenEntity> findByToken(String token);

    int deleteByUser(UserEntity userEntity);
}
