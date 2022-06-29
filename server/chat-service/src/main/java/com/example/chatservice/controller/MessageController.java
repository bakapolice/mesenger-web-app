package com.example.chatservice.controller;

import com.example.chatservice.model.Message;
import com.example.chatservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/msg")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<?> getChatMessages(@PathVariable String senderId, @PathVariable String recipientId){
        System.out.println("Sender = " + senderId + "/ recipient = " + recipientId);
        return ResponseEntity.ok(messageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<?> getChatMessagesByChatID(@PathVariable String chatId){
        return ResponseEntity.ok(messageService.findChatMessagesByChatId(chatId));
    }

    @GetMapping("/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity
                .ok(messageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<?> findMessage (@PathVariable Long id) {
        return ResponseEntity.ok(messageService.findById(id));
    }

}
