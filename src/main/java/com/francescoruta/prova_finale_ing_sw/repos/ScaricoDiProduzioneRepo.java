package com.francescoruta.prova_finale_ing_sw.repos;

import java.util.Optional;

import com.francescoruta.prova_finale_ing_sw.entities.ScaricoDiProduzioneEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScaricoDiProduzioneRepo extends JpaRepository<ScaricoDiProduzioneEntity, Long> {
	void deleteScaricoDiProduzioneById(Long id);
	Optional<ScaricoDiProduzioneEntity> findScaricoDiProduzioneById(Long id);
}
