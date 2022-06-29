package com.example.chatservice.service;

import com.example.chatservice.entity.ServerEntity;
import com.example.chatservice.entity.ServerMemberEntity;
import com.example.chatservice.entity.UserEntity;
import com.example.chatservice.exception.UserNotFoundException;
import com.example.chatservice.mapper.ServerToEntityMapper;
import com.example.chatservice.mapper.UserToEntityMapper;
import com.example.chatservice.model.Server;
import com.example.chatservice.model.ServerMember;
import com.example.chatservice.model.User;
import com.example.chatservice.repository.ServerRepository;
import com.example.chatservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService{
    private final UserRepository userRepository;
    private final UserToEntityMapper userMapper;
    private final ServerToEntityMapper serverMapper;
    private final ServerRepository serverRepository;

    @Override
    public User getUser(Long id){
        UserEntity userEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found!"));
        return userMapper.userEntityToUser(userEntity);
    }

    @Override
    public List<User> getAllUsers(){
        Iterable<UserEntity> iterable = userRepository.findAll();

        ArrayList<User> users = new ArrayList<>();
        for(UserEntity userEntity : iterable){
            users.add(userMapper.userEntityToUser(userEntity));
        }
        return users;
    }

    @Override
    public List<User> getServerMembers(Long id){
        ServerEntity serverEntity = serverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        List <ServerMemberEntity> memberEntities = serverEntity.getMembers();
        List<User> users = new ArrayList<>();
        for(ServerMemberEntity entity : memberEntities){
            users.add(userMapper.userEntityToUser(entity.getUser()));
        }
        return users;
    }

    @Override
    public List<Server> getAllServersByUserId(Long id) {
        List<ServerMember> members = getUser(id).getJoinedServers();
        ArrayList<Server> servers = new ArrayList<>();
        for(ServerMember member : members){
            servers.add(serverMapper.serverEntityToServer(member.getServer()));
        }
        return servers;
    }
}
