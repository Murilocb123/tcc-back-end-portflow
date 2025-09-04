package br.com.murilocb123.portflow.service;

import br.com.murilocb123.portflow.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserEntity create(UserEntity entity);
    UserEntity getById(UUID id);
    UserEntity update(UUID id, UserEntity entity);
    void delete(UUID id);
    Page<UserEntity> list(Pageable pageable);
}
