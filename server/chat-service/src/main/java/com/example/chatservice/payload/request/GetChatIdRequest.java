package com.example.chatservice.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetChatIdRequest {
    private String senderId;
    private String recipientId;
}
