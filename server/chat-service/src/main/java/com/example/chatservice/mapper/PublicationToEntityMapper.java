package com.example.chatservice.mapper;

import com.example.chatservice.entity.PublicationEntity;
import com.example.chatservice.model.Publication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicationToEntityMapper {
    PublicationEntity publicationToPublicationEntity(Publication publication);
    Publication publicationEntityToPublication(PublicationEntity publicationEntity);
}
