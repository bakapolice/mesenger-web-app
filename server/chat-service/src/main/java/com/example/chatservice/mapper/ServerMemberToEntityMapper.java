package com.example.chatservice.mapper;

import com.example.chatservice.entity.ServerMemberEntity;
import com.example.chatservice.model.ServerMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServerMemberToEntityMapper {
    ServerMemberEntity serverMemberToServerMemberEntity(ServerMember serverMember);
    ServerMember serverMemberEntityToServerMember(ServerMemberEntity serverMemberEntity);
}
