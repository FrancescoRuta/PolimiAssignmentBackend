package com.francescoruta.prova_finale_ing_sw.repos;

import com.francescoruta.prova_finale_ing_sw.entities.ArticoloDistintaBaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticoloDistintaBaseRepo extends JpaRepository<ArticoloDistintaBaseEntity, Long> {
	
}
