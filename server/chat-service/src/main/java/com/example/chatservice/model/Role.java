package com.example.chatservice.model;

import com.example.chatservice.entity.ServerMemberEntity;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Integer id;
    private ERole name;

//    private List<ServerMemberEntity> serverMembers;
}
