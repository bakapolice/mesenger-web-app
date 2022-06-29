package com.example.messenger.mapper;

import com.example.messenger.entity.AuthRoleEntity;
import com.example.messenger.models.AuthRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthRoleToEntityMapper {
    AuthRoleEntity authRoleToEntity(AuthRole authRole);
    AuthRole authRoleEntityToAuthRole(AuthRoleEntity authRoleEntity);
}
