package com.example.chatservice.repository;

import com.example.chatservice.entity.PublicChatRoomEntity;
import com.example.chatservice.entity.PublicChatRoomMemberEntity;
import org.springframework.data.repository.CrudRepository;

public interface PublicChatRoomMemberRepository extends CrudRepository<PublicChatRoomMemberEntity, Long> {
}
