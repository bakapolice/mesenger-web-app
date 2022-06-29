package com.example.chatservice.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewServerRequest {
    private String serverName;
    private String serverDescription;
    private String ownerId;
}
