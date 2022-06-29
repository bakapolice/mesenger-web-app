package com.example.chatservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login; // логин пользователя
    private String password; // пароль пользователя в виде hash-значения
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ServerMemberEntity> joinedServers;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PublicChatRoomMemberEntity> joinedChatRooms;
}
