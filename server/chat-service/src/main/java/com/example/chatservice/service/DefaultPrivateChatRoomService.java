package com.example.chatservice.service;


import com.example.chatservice.entity.PrivateChatRoomEntity;
import com.example.chatservice.exception.ResourceNotFoundException;
import com.example.chatservice.mapper.PrivateChatRoomMapper;
import com.example.chatservice.model.PrivateChatRoom;
import com.example.chatservice.repository.PrivateChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultPrivateChatRoomService implements PrivateChatRoomService {

    private final PrivateChatRoomRepository privateChatRoomRepository;
    private final PrivateChatRoomMapper privateChatRoomMapper;

    @Override
    public String getChatId(String senderId, String senderFirstName, String senderLastName, String recipientId, String recipientFirstName, String recipientLastName, boolean createIfNotExist) {
        Optional<PrivateChatRoomEntity> entity = privateChatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);

        String chatId = "";
        if (entity.isPresent())
            chatId = entity.get().getChatId();
        else {
            if (createIfNotExist) {
                chatId =
                        String.format("%s_%s", senderId, recipientId);

                privateChatRoomRepository.save(new PrivateChatRoomEntity(null, chatId, senderId,  recipientId, recipientFirstName, recipientLastName));
                if(!senderId.equals(recipientId)) privateChatRoomRepository.save(new PrivateChatRoomEntity(null, chatId, recipientId, senderId, senderFirstName, senderLastName));
            }
        }
        return chatId;
    }

    @Override
    public String getRecipientIdByChatAndSenderId(String chatId, String senderId) {
        PrivateChatRoomEntity entity = privateChatRoomRepository.findByChatIdAndSenderId(chatId, senderId)
                .orElseThrow(()-> new ResourceNotFoundException("Chat not found"));
        return entity.getRecipientId();
    }

    @Override
    public List<PrivateChatRoom> getAllChatsBySenderId(String senderId) {
        Iterable<PrivateChatRoomEntity> iterable = privateChatRoomRepository.findAllBySenderId(senderId);
        ArrayList<PrivateChatRoom> chatRooms = new ArrayList<>();
        for(PrivateChatRoomEntity entity : iterable){
            chatRooms.add(privateChatRoomMapper.entityToPrivateChatRoom(entity));
        }
        return chatRooms;
    }
}
