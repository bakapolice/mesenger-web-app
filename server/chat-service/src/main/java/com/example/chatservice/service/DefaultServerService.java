package com.example.chatservice.service;


import com.example.chatservice.entity.*;
import com.example.chatservice.exception.UserNotFoundException;
import com.example.chatservice.mapper.PublicChatRoomEntityMapper;
import com.example.chatservice.mapper.ServerToEntityMapper;
import com.example.chatservice.model.*;
import com.example.chatservice.payload.request.JoinServerRequest;
import com.example.chatservice.payload.request.NewServerRequest;
import com.example.chatservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultServerService implements ServerService {
    private final ServerRepository serverRepository;
    private final PublicChatRoomRepository chatRoomRepository;
    private final ServerToEntityMapper serverMapper;
    private final PublicChatRoomEntityMapper chatRoomEntityMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ServerMemberRepository serverMemberRepository;
    private final PublicChatRoomRepository publicChatRoomRepository;
    private final PublicChatRoomMemberRepository publicChatRoomMemberRepository;

    @Override
    public List<Server> getAllServers() {
        Iterable<ServerEntity> iterable = serverRepository.findAll();
        ArrayList<Server> servers = new ArrayList<>();
        for(ServerEntity entity : iterable){
            servers.add(serverMapper.serverEntityToServer(entity));
        }
        System.out.println("ALL SERVERS: " + servers);
        return servers;
    }

    @Override
    public Server getServer(Long serverId){
        ServerEntity serverEntity = serverRepository.findById(serverId)
                .orElseThrow(()-> new RuntimeException("Not found!"));
        return serverMapper.serverEntityToServer(serverEntity);
    }

    @Override
    public void create(NewServerRequest serverRequest) {
        ServerEntity server = serverRepository.save(new ServerEntity(null, serverRequest.getServerName(), serverRequest.getServerDescription(), serverRequest.getOwnerId(), null, null));

        UserEntity user = userRepository.findById(Long.valueOf(serverRequest.getOwnerId()))
                .orElseThrow(()-> new UserNotFoundException("User not found!"));
        RoleEntity roleAdmin = roleRepository.findByName(ERole.ADMIN)
                .orElseThrow(()-> new RuntimeException("Not found!"));
        RoleEntity roleMember = roleRepository.findByName(ERole.MEMBER)
                .orElseThrow(()-> new RuntimeException("Not found!"));
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleAdmin);
        roles.add(roleMember);

        ServerMemberEntity newServerMember = new ServerMemberEntity();
        newServerMember.setServer(server);
        newServerMember.setUser(user);
        newServerMember.setRoles(roles);

        ServerMemberEntity serverMember = serverMemberRepository.save(newServerMember);

        ArrayList<ServerMemberEntity> members = new ArrayList<>();
        members.add(serverMember);

        if(user.getJoinedServers() == null)
        {
            user.setJoinedServers(members);
        }
        else user.getJoinedServers().add(serverMember);
        userRepository.save(user);


        List<PublicChatRoomMemberEntity> chatRoomMembers = new ArrayList<>();
        PublicChatRoomEntity general = publicChatRoomRepository.save(new PublicChatRoomEntity(null, "general", "Main channel", server, null));
        PublicChatRoomMemberEntity firstMember = publicChatRoomMemberRepository.save(new PublicChatRoomMemberEntity(null, general, user));

        chatRoomMembers.add(firstMember);
        general.setMembers(chatRoomMembers);
        publicChatRoomRepository.save(general);

        List<PublicChatRoomEntity> publicChatRooms = new ArrayList<>();
        publicChatRooms.add(general);

        if(server.getMembers() == null)
        {
            server.setMembers(members);
        }
        else server.getMembers().add(serverMember);
        server.setPublicChatRooms(publicChatRooms);
        serverRepository.save(server);
    }

    @Override
    public String join(JoinServerRequest request) {
        ServerEntity server = serverRepository.findById(request.getServerId())
                .orElseThrow(() -> new RuntimeException("Not found!"));
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new UserNotFoundException("Not found!"));
        RoleEntity roleMember = roleRepository.findByName(ERole.MEMBER)
                .orElseThrow(()-> new RuntimeException("Not found!"));
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleMember);
        ServerMemberEntity serverMember = serverMemberRepository.save(new ServerMemberEntity(null, server, user, roles));

        List<PublicChatRoomEntity> iterable = server.getPublicChatRooms();
        for(PublicChatRoomEntity entity : iterable){
            PublicChatRoomMemberEntity memberEntity = publicChatRoomMemberRepository.save(new PublicChatRoomMemberEntity(null, entity, user));
            entity.getMembers().add(memberEntity);
            publicChatRoomRepository.save(entity);
        }
        serverRepository.save(server);
        return "Joined";
    }

    @Override
    public List<PublicChatRoom> getServerChats(Long serverId) {
        return getServer(serverId).getPublicChatRooms();
    }

}
