package com.francescoruta.prova_finale_ing_sw.repos;

import java.util.List;
import java.util.Optional;

import com.francescoruta.prova_finale_ing_sw.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
	void deleteUserById(Long id);
	Optional<UserEntity> findUserByUsername(String username);
	Optional<UserEntity> findUserById(Long id);
	List<UserEntity> findAllByIsSystem(Boolean isSystem);
}