package com.example.chatservice.controller;

import com.example.chatservice.payload.request.JoinChatRoomRequest;
import com.example.chatservice.payload.request.NewPublicChatRequest;
import com.example.chatservice.service.PrivateChatRoomService;
import com.example.chatservice.service.PublicChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {
    private final PrivateChatRoomService privateChatRoomService;
    private final PublicChatRoomService publicChatRoomService;

    @GetMapping("/{senderId}/{recipientId}")
    public String getChatId(@PathVariable String senderId, @PathVariable String recipientId){
        return privateChatRoomService.getChatId(senderId, "", "", recipientId, "", "", false);
    }

    @GetMapping("/{chatId}/{senderId}/recipient")
    public String getRecipientIdByChatAndSenderID(@PathVariable String chatId, @PathVariable String senderId){
        return privateChatRoomService.getRecipientIdByChatAndSenderId(chatId, senderId);
    }

    @GetMapping("/dialogs/{senderId}")
    public ResponseEntity<?> getAllChatsBySenderId(@PathVariable String senderId){
        return ResponseEntity.ok(privateChatRoomService.getAllChatsBySenderId(senderId));
    }
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinChatRoomRequest joinRequest){
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody NewPublicChatRequest request){
        publicChatRoomService.create(request);
        return ResponseEntity.ok("Created");
    }
}
