package com.francescoruta.prova_finale_ing_sw.repos;

import java.util.Optional;

import com.francescoruta.prova_finale_ing_sw.entities.DistintaBaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DistintaBaseRepo extends JpaRepository<DistintaBaseEntity, Long> {
	void deleteDistintaBaseById(Long id);
	Optional<DistintaBaseEntity> findDistintaBaseById(Long id);
}
