package com.example.chatservice.payload.request;

import lombok.Data;

@Data
public class AddPublicationRequest {
    private String title;
    private String description;
}
