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
@Table(name = "server")
public class ServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serverName;
    private String serverDescription;
    private String ownerId;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "server_id", referencedColumnName = "id")

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "server")
    private List<PublicChatRoomEntity> publicChatRooms;

    @JsonIgnore
    @OneToMany(mappedBy = "server")
    List<ServerMemberEntity> members;
}
