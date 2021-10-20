package com.francescoruta.prova_finale_ing_sw.services;

import java.time.LocalDateTime;
import java.util.List;

import com.francescoruta.prova_finale_ing_sw.entities.ArticoloEntity;
import com.francescoruta.prova_finale_ing_sw.entities.ArticoloQtaUpdateEntity;
import com.francescoruta.prova_finale_ing_sw.models.Articolo;
import com.francescoruta.prova_finale_ing_sw.models.ArticoloQtaUpdate;
import com.francescoruta.prova_finale_ing_sw.models.ArticoloQtaUpdateReason;
import com.francescoruta.prova_finale_ing_sw.repos.ArticoloQtaUpdateRepo;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.DefaultMapper;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.FieldMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticoloQtaUpdateService {
	private final ArticoloQtaUpdateRepo articoloQtaUpdateRepo;
	
	@Autowired
	public ArticoloQtaUpdateService(ArticoloQtaUpdateRepo articoloQtaUpdateRepo) {
		this.articoloQtaUpdateRepo = articoloQtaUpdateRepo;
	}
	
	/**
	 * Questa funzione aggiunge alle QTAUpdates dell'articolo la quantità corrente.
	 * QUESTA FUNZIONE NON EFFETTUA IL SALVATAGGIO DEI DATI SU DB.
	 * @param articolo entità dell'articolo con la quantità già aggiornata
	 * @param reason motivo della richiesta di aggiornamento della quantità di giacenza
	 */
	public void registerChange(ArticoloEntity articolo, ArticoloQtaUpdateReason reason) {
		ArticoloQtaUpdateEntity articoloQtaUpdate = new ArticoloQtaUpdateEntity();
		articoloQtaUpdate.setDateTime(LocalDateTime.now());
		articoloQtaUpdate.setReason(reason);
		articoloQtaUpdate.setQta(articolo.getQtaGiacenza());
		articolo.addQtaUpdates(articoloQtaUpdate);
	}
	
	public List<ArticoloQtaUpdate> getAll() {
		DefaultMapper<ArticoloQtaUpdateEntity, ArticoloQtaUpdate> articoloQtaUpdateMapper = DefaultMapper.mapper(ArticoloQtaUpdate.class);
		articoloQtaUpdateMapper.addMapping(FieldMapper.map("articolo", Articolo.class));
		return articoloQtaUpdateMapper.mapCollection(articoloQtaUpdateRepo.findAll());
	}
	
}
