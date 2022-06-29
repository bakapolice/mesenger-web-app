package com.example.chatservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "public_chat_rooms")
public class PublicChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String chatName;
    private String chatDescription;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "server_id", nullable = false)
    private ServerEntity server;

    @JsonIgnore
    @OneToMany(mappedBy = "publicChatRoom")
    List<PublicChatRoomMemberEntity> members;
}
