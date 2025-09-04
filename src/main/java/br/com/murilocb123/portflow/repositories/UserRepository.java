package br.com.murilocb123.portflow.repositories;

import br.com.murilocb123.portflow.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * from app_user where email = :email", nativeQuery = true)
    Optional<UserEntity> findByEmail(@Param("email") String email);
}