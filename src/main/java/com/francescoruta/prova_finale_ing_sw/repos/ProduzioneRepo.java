package com.francescoruta.prova_finale_ing_sw.repos;

import java.util.List;
import java.util.Optional;

import com.francescoruta.prova_finale_ing_sw.entities.ProduzioneEntity;
import com.francescoruta.prova_finale_ing_sw.models.StatoProduzione;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduzioneRepo extends JpaRepository<ProduzioneEntity, Long> {
	void deleteProduzioneById(Long id);
	Optional<ProduzioneEntity> findProduzioneById(Long id);
	List<ProduzioneEntity> findAllByStato(StatoProduzione stato);
}
