package com.example.chatservice.model;

import com.example.chatservice.entity.RoleEntity;
import com.example.chatservice.entity.ServerEntity;
import com.example.chatservice.entity.UserEntity;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerMember {
    private Long id;
    private ServerEntity server;
    private UserEntity user;
    private List<RoleEntity> roles;
}
