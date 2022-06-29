package com.example.chatservice.service;

import com.example.chatservice.model.PrivateChatRoom;

import java.util.List;
import java.util.Optional;

public interface PrivateChatRoomService {
    String getChatId(String senderId, String senderFirstName, String senderLastName, String recipientId, String recipientFirstName, String recipientLastName, boolean createIfNotExist);
    String getRecipientIdByChatAndSenderId(String chatId, String senderId);
    List<PrivateChatRoom> getAllChatsBySenderId(String senderId);
}
