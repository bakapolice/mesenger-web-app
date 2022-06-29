package com.example.chatservice.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinChatRoomRequest {
    private String chatRoomId;
    private String userId;
}
