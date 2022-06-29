package com.example.chatservice.mapper;

import com.example.chatservice.entity.UserEntity;
import com.example.chatservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserToEntityMapper {
    UserEntity userToUserEntity(User user);
    User userEntityToUser(UserEntity userEntity);
}
