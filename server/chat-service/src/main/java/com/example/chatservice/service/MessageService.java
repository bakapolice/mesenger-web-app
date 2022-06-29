package com.example.chatservice.service;


import com.example.chatservice.model.Message;
import com.example.chatservice.model.Status;

import java.util.ArrayList;
import java.util.List;

public interface MessageService {
    Message save(Message message);
    long countNewMessages(String senderId, String recipientId);
    List<Message> findChatMessages(String senderId, String recipientId);
    List<Message> findChatMessagesByChatId(String chatId);
    Message findById(Long id);
    void updateStatuses(ArrayList<Message> messages, Status status);

}
