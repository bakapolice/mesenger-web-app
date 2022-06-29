package com.example.chatservice.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPublicChatRequest {
    private String chatName;
    private String chatDescription;
    private Long serverId;
    private String ownerId;
}
