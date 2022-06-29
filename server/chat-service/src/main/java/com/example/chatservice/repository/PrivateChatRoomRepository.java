package com.example.chatservice.repository;

import com.example.chatservice.entity.PrivateChatRoomEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrivateChatRoomRepository extends CrudRepository<PrivateChatRoomEntity, Long> {
    Optional<PrivateChatRoomEntity> findBySenderIdAndRecipientId(String senderId, String recipientId);
    Optional<PrivateChatRoomEntity> findByChatIdAndSenderId(String chatId, String senderId);
    Iterable<PrivateChatRoomEntity> findAllBySenderId(String senderId);

}
