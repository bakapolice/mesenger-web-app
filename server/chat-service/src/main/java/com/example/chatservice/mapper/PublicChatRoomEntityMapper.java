package com.example.chatservice.mapper;

import com.example.chatservice.entity.PublicChatRoomEntity;
import com.example.chatservice.model.PublicChatRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicChatRoomEntityMapper {
    PublicChatRoomEntity publicChatRoomToEntity(PublicChatRoom publicChatRoom);
    PublicChatRoom entityToPublicChatRoom(PublicChatRoomEntity publicChatRoomEntity);
}
