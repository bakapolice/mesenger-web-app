package com.example.chatservice.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinServerRequest {
    private Long serverId;
    private Long userId;
}
