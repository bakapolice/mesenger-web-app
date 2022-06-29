package com.example.chatservice.service;

import com.example.chatservice.entity.MessageEntity;
import com.example.chatservice.exception.ResourceNotFoundException;
import com.example.chatservice.mapper.MessageToEntityMapper;
import com.example.chatservice.model.Message;
import com.example.chatservice.model.Status;
import com.example.chatservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final PrivateChatRoomService privateChatRoomService;
    private final MessageToEntityMapper messageMapper;

    @Override
    public Message save(Message message) {
        message.setStatus(Status.RECEIVED);
        messageRepository.save(messageMapper.messageToMessageEntity(message));
        return message;
    }

    @Override
    public long countNewMessages(String senderId, String recipientId) {
        return messageRepository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, Status.RECEIVED);
    }

    @Override
    public List<Message> findChatMessages(String senderId, String recipientId) {
        String chatId = privateChatRoomService.getChatId(senderId, "", "",
                recipientId, "", "", false);
        ArrayList<Message> messages = findMessagesByChatId(chatId);
        if (messages.size() > 0) {
            updateStatuses(messages, Status.DELIVERED);
        }

        return messages;
    }

    private ArrayList<Message> findMessagesByChatId(String chatId) {
        Iterable<MessageEntity> iterable = messageRepository.findAllByChatId(chatId);
        ArrayList<Message> messages = new ArrayList<>();
        for (MessageEntity messageEntity : iterable) {
            messages.add(messageMapper.messageEntityToMessage(messageEntity));
        }
        return messages;
    }

    @Override
    public List<Message> findChatMessagesByChatId(String chatId) {
        ArrayList<Message> messages = findMessagesByChatId(chatId);
        if (messages.size() > 0) {
            updateStatuses(messages, Status.DELIVERED);
        }
        return messages;
    }

    @Override
    public Message findById(Long id) {
        return messageRepository.findById(id)
                .map(message -> {
                    message.setStatus(Status.DELIVERED);
                    return messageMapper.messageEntityToMessage(messageRepository.save(message));
                })
                .orElseThrow(() -> new ResourceNotFoundException("can't find message(" + id + ")"));
    }

    @Override
    public void updateStatuses(ArrayList<Message> messages, Status status) {
        ArrayList<MessageEntity> messageEntities = new ArrayList<>();
        for (Message message : messages) {
            message.setStatus(Status.DELIVERED);
            messageEntities.add(messageMapper.messageToMessageEntity(message));
        }
        if (!messageEntities.isEmpty()) messageRepository.saveAll(messageEntities);
    }
}
