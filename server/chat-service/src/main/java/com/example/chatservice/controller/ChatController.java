package com.example.chatservice.controller;


import com.example.chatservice.exception.ResourceNotFoundException;
import com.example.chatservice.mapper.MessageToEntityMapper;
import com.example.chatservice.model.Message;
import com.example.chatservice.service.MessageService;
import com.example.chatservice.service.PrivateChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final PrivateChatRoomService privateChatRoomService;
    private final MessageToEntityMapper messageMapper;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        System.out.println("Message : " + message);
        String chatId = privateChatRoomService
                .getChatId(message.getSenderId(), message.getSenderFirstName(), message.getSenderLastName(),
                        message.getRecipientId(), message.getRecipientFirstName(), message.getRecipientLastName(), true);
        if (chatId.isEmpty()) throw new ResourceNotFoundException("Chat not found!");

        System.out.println("chatId = " + chatId);
        message.setChatId(chatId);
        Message saved = messageService.save(message);
        simpMessagingTemplate.convertAndSendToUser(
                saved.getRecipientId(), "/queue/messages",
                saved);
    }

    @MessageMapping("/chatroom/public/{roomId}")
    public Message receivePublicMessage(@DestinationVariable String roomId, @Payload Message message) {
        messageService.save(message);
        simpMessagingTemplate.convertAndSend("/chatroom/public/"+roomId, message);
        return message;
    }

    @MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getRecipientId(), "/private", message);
        return message;
    }
}
