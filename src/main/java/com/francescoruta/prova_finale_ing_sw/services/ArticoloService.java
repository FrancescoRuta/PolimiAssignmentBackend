package com.francescoruta.prova_finale_ing_sw.services;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import com.francescoruta.prova_finale_ing_sw.exceptions.ConstraintException;
import com.francescoruta.prova_finale_ing_sw.exceptions.NotAnUpdate;
import com.francescoruta.prova_finale_ing_sw.exceptions.NotFoundException;
import com.francescoruta.prova_finale_ing_sw.models.*;
import com.francescoruta.prova_finale_ing_sw.entities.*;
import com.francescoruta.prova_finale_ing_sw.repos.ArticoloRepo;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticoloService {
	private final ArticoloRepo articoloRepo;
	private final ArticoloQtaUpdateService articoloQtaUpdateService;
	
	@Autowired
	public ArticoloService(ArticoloRepo articoloRepo, ArticoloQtaUpdateService articoloQtaUpdateService) {
		this.articoloRepo = articoloRepo;
		this.articoloQtaUpdateService = articoloQtaUpdateService;
	}
	
	/**
	 * Aggiunge un nuovo articolo
	 * @param articolo model articolo da salvare
	 */
	public Long add(Articolo articolo) {
		articolo.setId(null);
		articolo.setQtaGiacenza(0.0);
		articolo.getDistinteBase().stream().forEach(d -> {
			if (d.getArticoliDistintaBase().stream().filter(a -> a.getQta() <= 0).findAny().isPresent())
				throw new ConstraintException("La quantità deve essare maggiore di zero");
		});
		return articoloRepo.save(deepMap(() -> new ArticoloEntity()).map(articolo)).getId();
	}
	
	public List<Articolo> getAll() {
		return DefaultMapper.mapCollection(Articolo.class, articoloRepo.findAll());
	}
	
	/**
	 * Preleva gli articoli dal DB includendo i dati necessari ad avviare una nuova produzione
	 */
	public List<Articolo> getAllWithDistintaBase() {
		DefaultMapper<ArticoloEntity, Articolo> articoloMapper = DefaultMapper.mapper(Articolo.class);
		articoloMapper.addMapping(FieldMapper.mapCollection("distinteBase", DistintaBase.class));
		return articoloMapper.mapCollection(articoloRepo.findAll());
	}
	
	/**
	 * Esegue l'update di un articolo
	 * @param articolo model articolo da salvare
	 */
	public Long update(Articolo articolo) {
		if (articolo.getId() == null) throw new NotAnUpdate();
		articolo.setQtaGiacenza(null);
		articolo.getDistinteBase().stream().forEach(d -> {
			if (d.getArticoliDistintaBase().stream().filter(a -> a.getQta() <= 0).findAny().isPresent())
				throw new ConstraintException("La quantità deve essare maggiore di zero");
		});
		ArticoloEntity oldArticolo = getEntityById(articolo.getId());
		return articoloRepo.save(deepMap(() -> oldArticolo).map(articolo)).getId();
	}
	
	public Articolo getById(Long id) {
		return deepMap().map(getEntityById(id));
	}
	
	public ArticoloEntity getEntityById(Long id) {
		return articoloRepo.findArticoloById(id).orElseThrow(() -> new NotFoundException("ID '" + id + "'' was not found"));
	}
	
	/**
	 * Rettifica la QtaGiacenza di un articolo
	 * @param id id articolo da rettificare
	 * @param qta nuova quantità dell'articolo
	 */
	public void rettificaGiacenza(Long id, Double qta) {
		ArticoloEntity articolo = getEntityById(id);
		articolo.setQtaGiacenza(qta);
		articoloQtaUpdateService.registerChange(articolo, ArticoloQtaUpdateReason.RETTIFICA);
		articoloRepo.save(articolo);
	}
	
	//Generazione del mapper per la lettura dell'articolo da DB
	private DefaultMapper<ArticoloEntity, Articolo> deepMap() {
		DefaultMapper<ArticoloEntity, Articolo> articoloMapper = DefaultMapper.mapper(Articolo.class);
		DefaultMapper<DistintaBaseEntity, DistintaBase> distintaBaseMapper = DefaultMapper.mapper(DistintaBase.class);
		DefaultMapper<ArticoloQtaUpdateEntity, ArticoloQtaUpdate> qtaUpdatesMapper = DefaultMapper.mapper(ArticoloQtaUpdate.class);
		DefaultMapper<ArticoloDistintaBaseEntity, ArticoloDistintaBase> articoloDistintaBaseMapper = DefaultMapper.mapper(ArticoloDistintaBase.class);
		articoloDistintaBaseMapper.addMapping(FieldMapper.map("articolo", Articolo.class));
		distintaBaseMapper.addMapping(FieldMapper.mapCollection("articoliDistintaBase", articoloDistintaBaseMapper));
		articoloMapper.addMapping(FieldMapper.mapCollection("distinteBase", distintaBaseMapper));
		articoloMapper.addMapping(FieldMapper.mapCollection("qtaUpdates", qtaUpdatesMapper));
		return articoloMapper;
	}
	
	//Generazione del mapper per il salvataggio dell'articolo su DB
	private DefaultMapper<Articolo, ArticoloEntity> deepMap(Supplier<ArticoloEntity> articoloSupplier) {
		DefaultMapper<Articolo, ArticoloEntity> articoloMapper = DefaultMapper.mapper(ArticoloEntity.class, articoloSupplier, false);
		DefaultMapper<DistintaBase, DistintaBaseEntity> distintaBaseMapper = DefaultMapper.mapper(DistintaBaseEntity.class);
		DefaultMapper<ArticoloDistintaBase, ArticoloDistintaBaseEntity> articoloDistintaBaseMapper = DefaultMapper.mapper(ArticoloDistintaBaseEntity.class);
		articoloDistintaBaseMapper.addMapping(FieldMapper.map("articolo", ArticoloEntity.class));
		distintaBaseMapper.addMapping(FieldMapper.mapCollection("articoliDistintaBase", articoloDistintaBaseMapper));
		articoloMapper.addMapping(FieldMapper.mapCollection("distinteBase", distintaBaseMapper));
		return articoloMapper;
	}

	/**
	 * Questa funzione effettua lo scarico degli articoli dal magazzino per occuparli prima della produzione
	 * @param qtaProdotta quantità prodotta
	 * @param scarichiDiProduzione lista degli articoli della distinta base
	 */
	public void scaricoDiProduzione(Double qtaProdotta, Collection<ScaricoDiProduzioneEntity> scarichiDiProduzione) {
		scarichiDiProduzione.stream().forEach(s -> {
			Double qta = s.getQta() * qtaProdotta;
			ArticoloEntity articolo = s.getArticolo();
			articolo.setQtaGiacenza(articolo.getQtaGiacenza() - qta);
			articoloQtaUpdateService.registerChange(articolo, ArticoloQtaUpdateReason.SCARICO_DI_PRODUZIONE);
			articoloRepo.save(articolo);
		});
	}
	
	/**
	 * Questa funzione annulla uno scarico di produzione precedentemente effettuato
	 * @param qtaProdotta quantità prodotta
	 * @param scarichiDiProduzione lista degli articoli della distinta base
	 */
	public void annullaScaricoDiProduzione(Double qtaProdotta, Collection<ScaricoDiProduzioneEntity> scarichiDiProduzione) {
		scarichiDiProduzione.stream().forEach(s -> {
			Double qta = s.getQta() * qtaProdotta;
			ArticoloEntity articolo = s.getArticolo();
			articolo.setQtaGiacenza(articolo.getQtaGiacenza() + qta);
			articoloQtaUpdateService.registerChange(articolo, ArticoloQtaUpdateReason.ANNULLA_SCARICO_DI_PRODUZIONE);
			articoloRepo.save(articolo);
		});
	}
	
	/**
	 * Questa funzione corregge uno scarico di produzione precedentemente effettuato
	 * @param qtaProdottaOld quantità prodotta da correggere
	 * @param qtaProdotta nuova quantità prodotta
	 * @param scarichiDiProduzione lista degli articoli della distinta base
	 */
	public void modificaScaricoDiProduzione(Double qtaProdottaOld, Double qtaProdotta, Collection<ScaricoDiProduzioneEntity> scarichiDiProduzione) {
		scarichiDiProduzione.stream().forEach(s -> {
			Double qta = s.getQta() * (qtaProdottaOld - qtaProdotta);
			ArticoloEntity articolo = s.getArticolo();
			articolo.setQtaGiacenza(articolo.getQtaGiacenza() + qta);
			articoloQtaUpdateService.registerChange(articolo, ArticoloQtaUpdateReason.MODIFICA_SCARICO_DI_PRODUZIONE);
			articoloRepo.save(articolo);
		});
	}
	
	/**
	 * Effettua il carico in magazzino dopo il completamento di una produzione
	 * @param qtaProdotta quantità prodotta
	 * @param articolo articolo prodotto
	 */
	public void caricoDiProduzione(Double qtaProdotta, ArticoloEntity articolo) {
		articolo.setQtaGiacenza(articolo.getQtaGiacenza() + qtaProdotta);
		articoloQtaUpdateService.registerChange(articolo, ArticoloQtaUpdateReason.CARICO_DI_PRODUZIONE);
		articoloRepo.save(articolo);
	}

	/**
	 * Elimina un articolo con relativo check sui constraints
	 * @param id id articolo da eliminare
	 */
	public void elimina(Long id) {
		ArticoloEntity articolo = getEntityById(id);
		//La verifica di QtaUpdates implica anche l'assenza di produzioni effettuate
		if (articolo.getQtaUpdates().size() > 0) throw new ConstraintException("Impossibile eliminare l'articolo, poichè esistono dei movimenti di magazzino ad esso associati.");
		if (articolo.getProduzioni().size() > 0) throw new ConstraintException("Impossibile eliminare l'articolo, poichè è presente in almeno una produzione.");
		if (articolo.getArticoloDistinteBase().size() > 0) throw new ConstraintException("Impossibile eliminare l'articolo, poichè è presente in almeno una distinta base.");
		articoloRepo.delete(articolo);
	}
	
}
