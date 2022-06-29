package com.example.chatservice.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    private Long id;
    private String serverName;
    private String serverDescription;
    private String ownerId;

    private List<PublicChatRoom> publicChatRooms;
    List<ServerMember> members;
}
