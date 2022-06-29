package com.example.chatservice.repository;

import com.example.chatservice.entity.PublicationEntity;
import org.springframework.data.repository.CrudRepository;

public interface PublicationRepository extends CrudRepository<PublicationEntity, Long> {
}
