package com.example.chatservice.controller;



import com.example.chatservice.model.Server;
import com.example.chatservice.model.ServerMember;
import com.example.chatservice.model.User;
import com.example.chatservice.payload.request.JoinServerRequest;
import com.example.chatservice.payload.request.NewServerRequest;

import com.example.chatservice.service.ServerService;
import com.example.chatservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/server")
public class ServerController {

    private final ServerService serverService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody NewServerRequest serverRequest){
        serverService.create(serverRequest);
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllServers(){
        return ResponseEntity.ok(serverService.getAllServers());
    }

    @GetMapping("/all/{userId}")
    public List<Server> getUserServers(@PathVariable Long userId){
        return userService.getAllServersByUserId(userId);
    }

    @GetMapping("/{id}/users")
    public List<User> getServerMembers(@PathVariable Long id){
        return userService.getServerMembers(id);
    }

    @GetMapping("/chats/{id}")
    public ResponseEntity<?> getChatsByServerId(@PathVariable Long id){
        return ResponseEntity.ok(serverService.getServerChats(id));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinServerRequest request){
        return ResponseEntity.ok(serverService.join(request));
    }
}
