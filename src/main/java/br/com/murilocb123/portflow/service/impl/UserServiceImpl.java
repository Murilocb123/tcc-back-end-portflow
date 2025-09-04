package br.com.murilocb123.portflow.service.impl;

import br.com.murilocb123.portflow.domain.entities.UserEntity;
import br.com.murilocb123.portflow.repositories.UserRepository;
import br.com.murilocb123.portflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity create(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public UserEntity getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public UserEntity update(UUID id, UserEntity entity) {
        UserEntity existing = getById(id);
        entity.setId(existing.getId());
        return userRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserEntity> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}

