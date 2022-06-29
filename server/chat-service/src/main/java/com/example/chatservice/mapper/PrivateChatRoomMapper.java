package com.example.chatservice.mapper;

import com.example.chatservice.entity.PrivateChatRoomEntity;
import com.example.chatservice.model.PrivateChatRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrivateChatRoomMapper {
    PrivateChatRoomEntity privateChatRoomToEntity(PrivateChatRoom privateChatRoom);
    PrivateChatRoom entityToPrivateChatRoom(PrivateChatRoomEntity privateChatRoomEntity);
}
