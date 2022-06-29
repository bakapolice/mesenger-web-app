package com.example.chatservice.model;

import com.example.chatservice.entity.PublicChatRoomMemberEntity;
import com.example.chatservice.entity.ServerMemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Long id;
    String login;
    String password;
    String firstName;
    String lastName;
    String email;

    private List<ServerMember> joinedServers;
    private List<PublicChatRoomMember> joinedChatRooms;
}
