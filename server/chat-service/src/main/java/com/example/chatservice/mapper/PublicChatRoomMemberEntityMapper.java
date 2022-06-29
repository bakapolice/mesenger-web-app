package com.example.chatservice.mapper;

import com.example.chatservice.entity.PublicChatRoomMemberEntity;
import com.example.chatservice.entity.ServerMemberEntity;
import com.example.chatservice.model.PublicChatRoomMember;
import com.example.chatservice.model.ServerMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicChatRoomMemberEntityMapper {
    PublicChatRoomMemberEntity publicChatRoomMemberToPublicChatRoomMemberEntity(PublicChatRoomMember publicChatRoomMember);
    PublicChatRoomMember publicChatRoomMemberEntityToPublicChatRoomMember(PublicChatRoomMemberEntity publicChatRoomMemberEntity);
}
