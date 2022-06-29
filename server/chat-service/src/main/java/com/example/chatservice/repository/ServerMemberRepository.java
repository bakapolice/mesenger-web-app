package com.example.chatservice.repository;

import com.example.chatservice.entity.ServerMemberEntity;
import org.springframework.data.repository.CrudRepository;

public interface ServerMemberRepository extends CrudRepository<ServerMemberEntity, Long> {
}
