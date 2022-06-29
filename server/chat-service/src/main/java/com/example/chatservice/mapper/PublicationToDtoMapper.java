package com.example.chatservice.mapper;

import com.example.chatservice.model.Publication;
import com.example.chatservice.payload.request.AddPublicationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublicationToDtoMapper {
    Publication AddPublicationRequestToPublication(AddPublicationRequest addPublicationRequest);
}
