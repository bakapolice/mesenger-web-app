package com.example.chatservice.mapper;

import com.example.chatservice.entity.MessageEntity;
import com.example.chatservice.model.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageToEntityMapper {
    MessageEntity messageToMessageEntity(Message message);
    Message messageEntityToMessage(MessageEntity message);
}
