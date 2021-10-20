package com.francescoruta.prova_finale_ing_sw.services;

import java.util.List;

import com.francescoruta.prova_finale_ing_sw.entities.ImpiantoDiProduzioneEntity;
import com.francescoruta.prova_finale_ing_sw.exceptions.*;
import com.francescoruta.prova_finale_ing_sw.models.ImpiantoDiProduzione;
import com.francescoruta.prova_finale_ing_sw.repos.ImpiantoDiProduzioneRepo;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.DefaultMapper;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpiantoDiProduzioneService {
	private final ImpiantoDiProduzioneRepo impiantoDiProduzioneRepo;
	
	@Autowired
	public ImpiantoDiProduzioneService(ImpiantoDiProduzioneRepo impiantoDiProduzioneRepo) {
		this.impiantoDiProduzioneRepo = impiantoDiProduzioneRepo;
	}
	
	public List<ImpiantoDiProduzione> getAll() {
		return DefaultMapper.mapCollection(ImpiantoDiProduzione.class, impiantoDiProduzioneRepo.findAll());
	}
	
	public Long add(ImpiantoDiProduzione impiantoDiProduzione) {
		Mapper<ImpiantoDiProduzione, ImpiantoDiProduzioneEntity> mapper = DefaultMapper.mapper(ImpiantoDiProduzioneEntity.class, false);
		impiantoDiProduzione.setId(null);
		impiantoDiProduzione.setProduzioni(null);
		return impiantoDiProduzioneRepo.save(mapper.map(impiantoDiProduzione)).getId();
	}
	
	public Long update(ImpiantoDiProduzione impiantoDiProduzione) {
		if (impiantoDiProduzione.getId() == null) throw new NotAnUpdate();
		Mapper<ImpiantoDiProduzione, ImpiantoDiProduzioneEntity> mapper = DefaultMapper.mapper(ImpiantoDiProduzioneEntity.class, false);
		impiantoDiProduzione.setProduzioni(null);
		return impiantoDiProduzioneRepo.save(mapper.map(impiantoDiProduzione)).getId();
	}

	/**
	 * Elimina un impianto di produzione con relativo check sui constraints
	 * @param id id impianto da eliminare
	 */
	public void elimina(Long id) {
		ImpiantoDiProduzioneEntity impiantoDiProduzione = impiantoDiProduzioneRepo.getById(id);
		if (impiantoDiProduzione == null) throw new NotFoundException("ID '" + id + "'' was not found");
		if (impiantoDiProduzione.getProduzioni().size() > 0) throw new ConstraintException("Impossibile eliminare l'impianto di produzione, poichè esistono delle produzioni ad esso associate.");
		impiantoDiProduzioneRepo.delete(impiantoDiProduzione);
	}
	
}
