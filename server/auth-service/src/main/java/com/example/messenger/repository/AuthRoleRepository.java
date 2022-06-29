package com.example.messenger.repository;

import com.example.messenger.entity.AuthRoleEntity;
import com.example.messenger.models.ERole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthRoleRepository extends CrudRepository<AuthRoleEntity, Long> {
    Optional<AuthRoleEntity> findByName(ERole name);
}
