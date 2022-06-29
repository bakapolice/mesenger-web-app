package com.example.chatservice.model;

import com.example.chatservice.entity.PublicChatRoomEntity;
import com.example.chatservice.entity.UserEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicChatRoomMember {
    private Long id;

    private PublicChatRoom publicChatRoom;
    private UserEntity user;
}
