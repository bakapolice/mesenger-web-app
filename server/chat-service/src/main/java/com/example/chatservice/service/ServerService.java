package com.example.chatservice.service;

import com.example.chatservice.model.PublicChatRoom;
import com.example.chatservice.model.Server;
import com.example.chatservice.model.ServerMember;
import com.example.chatservice.model.User;
import com.example.chatservice.payload.request.JoinServerRequest;
import com.example.chatservice.payload.request.NewServerRequest;

import java.util.List;

public interface ServerService {
    List<Server> getAllServers();
    List<PublicChatRoom> getServerChats(Long serverId);
    Server getServer(Long serverId);
    void create(NewServerRequest serverRequest);
    String join(JoinServerRequest request);
}
