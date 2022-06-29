package com.example.chatservice.model;

import com.example.chatservice.entity.PublicChatRoomMemberEntity;
import com.example.chatservice.entity.ServerEntity;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicChatRoom {
    private Long id;
    private String chatName;
    private String chatDescription;

    private List<ServerEntity> servers;
    List<PublicChatRoomMemberEntity> members;
}
