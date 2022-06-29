package com.example.chatservice.repository;

import com.example.chatservice.entity.ServerEntity;
import org.springframework.data.repository.CrudRepository;

public interface ServerRepository extends CrudRepository<ServerEntity, Long> {

}
