package com.example.chatservice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "public_chat_room_member")
public class PublicChatRoomMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "public_chat_room_id")
    private PublicChatRoomEntity publicChatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
