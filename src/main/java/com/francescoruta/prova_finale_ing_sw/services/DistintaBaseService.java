package com.francescoruta.prova_finale_ing_sw.services;

import java.util.List;

import com.francescoruta.prova_finale_ing_sw.exceptions.BadRequestException;
import com.francescoruta.prova_finale_ing_sw.exceptions.NotFoundException;
import com.francescoruta.prova_finale_ing_sw.entities.*;
import com.francescoruta.prova_finale_ing_sw.repos.DistintaBaseRepo;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistintaBaseService {
	private final DistintaBaseRepo distintaBaseRepo;
	
	@Autowired
	public DistintaBaseService(DistintaBaseRepo distintaBaseRepo) {
		this.distintaBaseRepo = distintaBaseRepo;
	}
	
	/**
	 * Genera gli scarichi di produzione necessari per una determinata distinta base.
	 * @param idArticolo id articolo prodotto
	 * @param idDistintaBase id distinta base selezionata
	 * @return
	 */
	public List<ScaricoDiProduzioneEntity> getScarichiDiProduzioneById(Long idArticolo, Long idDistintaBase) {
		DistintaBaseEntity distintaBase = distintaBaseRepo.findDistintaBaseById(idDistintaBase).orElseThrow(() -> new NotFoundException("ID '" + idDistintaBase + "'' was not found"));
		if (!distintaBase.getArticoloProdotto().getId().equals(idArticolo)) throw new BadRequestException("Articolo missmatch");
		DefaultMapper<ArticoloDistintaBaseEntity, ScaricoDiProduzioneEntity> scaricoDiProduzioneMapper = DefaultMapper.mapper(ScaricoDiProduzioneEntity.class);
		scaricoDiProduzioneMapper.addMapping(FieldMapper.map("articolo", ArticoloEntity.class));
		return scaricoDiProduzioneMapper.mapCollection(distintaBase.getArticoliDistintaBase());
	}
	
}
