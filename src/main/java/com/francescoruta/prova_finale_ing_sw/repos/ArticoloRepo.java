package com.francescoruta.prova_finale_ing_sw.repos;

import java.util.Optional;

import com.francescoruta.prova_finale_ing_sw.entities.ArticoloEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticoloRepo extends JpaRepository<ArticoloEntity, Long> {
	void deleteArticoloById(Long id);
	Optional<ArticoloEntity> findArticoloById(Long id);
}
