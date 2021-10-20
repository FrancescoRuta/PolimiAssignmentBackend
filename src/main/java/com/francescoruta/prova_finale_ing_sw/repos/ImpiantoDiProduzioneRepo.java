package com.francescoruta.prova_finale_ing_sw.repos;

import java.util.Optional;

import com.francescoruta.prova_finale_ing_sw.entities.ImpiantoDiProduzioneEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpiantoDiProduzioneRepo extends JpaRepository<ImpiantoDiProduzioneEntity, Long> {
	void deleteImpiantoDiProduzioneById(Long id);
	Optional<ImpiantoDiProduzioneEntity> findImpiantoDiProduzioneById(Long id);
}
