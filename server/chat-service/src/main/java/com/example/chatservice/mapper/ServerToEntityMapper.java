package com.example.chatservice.mapper;

import com.example.chatservice.entity.ServerEntity;
import com.example.chatservice.model.Server;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServerToEntityMapper {
    ServerEntity serverToServerEntity(Server server);
    Server serverEntityToServer(ServerEntity serverEntity);
}
