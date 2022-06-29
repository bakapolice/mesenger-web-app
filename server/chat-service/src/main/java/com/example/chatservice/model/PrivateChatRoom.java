package com.example.chatservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateChatRoom {
    private Long id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String recipientFirstName;
    private String recipientLastName;
}
