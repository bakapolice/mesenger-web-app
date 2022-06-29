package com.example.messenger.mapper;

import com.example.messenger.entity.UserEntity;
import com.example.messenger.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserToEntityMapper {
    UserEntity userToUserEntity(User user);
    User userEntityToUser(UserEntity userEntity);
}
