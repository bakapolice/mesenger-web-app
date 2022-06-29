package com.example.chatservice.repository;

import com.example.chatservice.entity.MessageEntity;
import com.example.chatservice.model.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
    long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, Status status);
    Iterable<MessageEntity> findAllByChatId(String chatId);
    Iterable<MessageEntity> findAllBySenderIdAndRecipientId(String senderId, String recipientId);

}
