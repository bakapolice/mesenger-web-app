package com.example.chatservice.service;

import com.example.chatservice.model.Server;
import com.example.chatservice.model.ServerMember;
import com.example.chatservice.model.User;

import java.util.List;

public interface UserService {
    User getUser(Long id);
    List<User> getAllUsers();
    List<Server> getAllServersByUserId(Long userId);
    List<User> getServerMembers(Long id);
}
