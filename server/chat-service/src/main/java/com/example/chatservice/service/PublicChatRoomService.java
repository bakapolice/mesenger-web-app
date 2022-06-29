package com.example.chatservice.service;

import com.example.chatservice.payload.request.NewPublicChatRequest;

public interface PublicChatRoomService {
    void create(NewPublicChatRequest request);
}
