package com.example.chatservice.service;

import com.example.chatservice.entity.*;
import com.example.chatservice.exception.UserNotFoundException;
import com.example.chatservice.mapper.ServerToEntityMapper;
import com.example.chatservice.model.ERole;
import com.example.chatservice.model.PublicChatRoomMember;
import com.example.chatservice.model.Server;
import com.example.chatservice.model.ServerMember;
import com.example.chatservice.payload.request.NewPublicChatRequest;
import com.example.chatservice.repository.PublicChatRoomMemberRepository;
import com.example.chatservice.repository.PublicChatRoomRepository;
import com.example.chatservice.repository.RoleRepository;
import com.example.chatservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultPublicChatRoomService implements PublicChatRoomService{

    private final PublicChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final PublicChatRoomMemberRepository publicChatRoomMemberRepository;
    private final ServerService serverService;
    private final ServerToEntityMapper serverToEntityMapper;

    @Override
    public void create(NewPublicChatRequest request) {
        Server server = serverService.getServer(request.getServerId());

        PublicChatRoomEntity publicChatRoom = chatRoomRepository
                .save(new PublicChatRoomEntity(null, request.getChatName(), request.getChatDescription(), serverToEntityMapper.serverToServerEntity(server), null));

        UserEntity user = userRepository.findById(Long.valueOf(request.getOwnerId()))
                .orElseThrow(()-> new UserNotFoundException("User not found!"));

        List<PublicChatRoomMemberEntity> publicChatMembers = new ArrayList<>();

        PublicChatRoomMemberEntity newChatMember = new PublicChatRoomMemberEntity(null, publicChatRoom, user);
        PublicChatRoomMemberEntity chatRoomMember = publicChatRoomMemberRepository.save(newChatMember);

        publicChatMembers.add(chatRoomMember);

        List<ServerMember> serverMembers = server.getMembers();
        for(ServerMember member : serverMembers){
            PublicChatRoomMemberEntity chatMemberEntity = publicChatRoomMemberRepository
                    .save((new PublicChatRoomMemberEntity(null, publicChatRoom, member.getUser())));
            publicChatMembers.add(chatMemberEntity);
        }


        if(user.getJoinedChatRooms() == null){
            user.setJoinedChatRooms(publicChatMembers);
        }else user.getJoinedChatRooms().addAll(publicChatMembers);
        userRepository.save(user);


        if(publicChatRoom.getMembers() == null){
            publicChatRoom.setMembers(publicChatMembers);
        }else publicChatRoom.getMembers().addAll(publicChatMembers);
        chatRoomRepository.save(publicChatRoom);
    }
}
