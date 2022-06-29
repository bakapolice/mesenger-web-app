package com.example.chatservice.repository;

import com.example.chatservice.entity.RoleEntity;
import com.example.chatservice.model.ERole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERole name);
}
