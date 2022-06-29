package com.example.messenger.mapper;

import com.example.messenger.entity.RefreshTokenEntity;
import com.example.messenger.models.RefreshToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenToEntityMapper {
    RefreshTokenEntity refreshTokenToEntity(RefreshToken refreshToken);
    RefreshToken refreshTokenEntityToRefreshToken(RefreshTokenEntity refreshTokenEntity);
}
