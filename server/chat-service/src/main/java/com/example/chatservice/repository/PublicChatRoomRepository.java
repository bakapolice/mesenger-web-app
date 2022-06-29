package com.example.chatservice.repository;

import com.example.chatservice.entity.PublicChatRoomEntity;
import org.springframework.data.repository.CrudRepository;

public interface PublicChatRoomRepository extends CrudRepository<PublicChatRoomEntity, Long> {
}
