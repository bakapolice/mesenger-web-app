package com.example.messenger.security.service;

import com.example.messenger.entity.UserEntity;
import com.example.messenger.models.User;
import com.example.messenger.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<MessageResponse> register(User user);
    void checkCredentials(String userId, String userSecret);
    UserEntity checkCredentialsByLogin(String login, String password);
    User getUser(Long id);
    List<User> getAllUsers();
}
