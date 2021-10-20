package com.francescoruta.prova_finale_ing_sw.services;

import java.time.LocalDateTime;
import java.util.List;

import com.francescoruta.prova_finale_ing_sw.entities.*;
import com.francescoruta.prova_finale_ing_sw.exceptions.BadRequestException;
import com.francescoruta.prova_finale_ing_sw.exceptions.ConstraintException;
import com.francescoruta.prova_finale_ing_sw.exceptions.NotAnUpdate;
import com.francescoruta.prova_finale_ing_sw.exceptions.NotFoundException;
import com.francescoruta.prova_finale_ing_sw.models.*;
import com.francescoruta.prova_finale_ing_sw.repos.ProduzioneRepo;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.DefaultMapper;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.FieldMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProduzioneService {
	private final ProduzioneRepo produzioneRepo;
	private final DistintaBaseService distintaBaseService;
	private final ArticoloService articoloService;
	
	@Autowired
	public ProduzioneService(ProduzioneRepo produzioneRepo, DistintaBaseService distintaBaseService, ArticoloService articoloService) {
		this.produzioneRepo = produzioneRepo;
		this.distintaBaseService = distintaBaseService;
		this.articoloService = articoloService;
	}
	
	/**
	 * Preleva una lista di produzioni con i dati necessari alla visualizzazione
	 * @param stato stato selezionato
	 * @return lista di produzioni corrispondenti allo stato selezionato
	 */
	private List<Produzione> getByStato(StatoProduzione stato) {
		DefaultMapper<ProduzioneEntity, Produzione> produzioneMapper = DefaultMapper.mapper(Produzione.class);
		produzioneMapper.addMapping(FieldMapper.map("articoloProdotto", Articolo.class));
		produzioneMapper.addMapping(FieldMapper.map("impiantoDiProduzione", ImpiantoDiProduzione.class));
		List<Produzione> ordiniDiProduzione = produzioneMapper.mapCollection(produzioneRepo.findAllByStato(stato));
		return ordiniDiProduzione;
	}
	
	public List<Produzione> getOrdiniDiProduzione() {
		return getByStato(StatoProduzione.NON_AVVIATA);
	}
	
	public List<Produzione> getProduzioniInCorso() {
		return getByStato(StatoProduzione.AVVIATA);
	}
	
	public List<Produzione> getProduzioniEffettuate() {
		return getByStato(StatoProduzione.COMPLETATA);
	}
	
	/**
	 * Crea un nuovo ordine di produzione, effettuando anche lo scarico delle materie prime
	 * @param produzione ordine di produzione da aggiungere
	 */
	@Transactional
	public Long addOrdine(Produzione produzione) {
		produzione.setId(null);
		if (produzione.getQtaProdotta() <= 0) throw new ConstraintException("La quantità prodotta deve essere maggiore di zero.");
		if (produzione.getArticoloProdotto() == null) throw new BadRequestException("Articolo is missing");
		if (produzione.getDistintaBase() == null) throw new BadRequestException("Distinta base is missing");
		if (produzione.getImpiantoDiProduzione() == null) throw new BadRequestException("Impianto di produzione is missing");
		
		//Creo un mapper per ProduzioneEntity che include impiantoDiProduzione
		DefaultMapper<Produzione, ProduzioneEntity> produzioneMapper = DefaultMapper.mapper(ProduzioneEntity.class);
		produzioneMapper.addMapping(FieldMapper.map("impiantoDiProduzione", ImpiantoDiProduzioneEntity.class));
		
		ProduzioneEntity produzioneEntity = produzioneMapper.map(produzione);
		produzioneEntity.setStato(StatoProduzione.NON_AVVIATA);
		Long idArticoloProdotto = produzione.getArticoloProdotto().getId();
		
		//Chiamo la funzione di conversione da DistintaBaseEntity a List<ScaricoDiProduzioneEntity>
		List<ScaricoDiProduzioneEntity> scarichiDiProduzione = distintaBaseService.getScarichiDiProduzioneById(idArticoloProdotto, produzione.getDistintaBase().getId());
		
		produzioneEntity.setArticoloProdotto(articoloService.getEntityById(produzione.getArticoloProdotto().getId()));
		
		//Salvo la produzione senza scarichi di produzione e recupero l'entità sincronizzata con il DB
		produzioneEntity = produzioneRepo.save(produzioneEntity);
		
		//Effettuo il set degli scarichi di produzione e salvo l'entità completa
		produzioneEntity.setScarichiDiProduzione(scarichiDiProduzione);
		produzioneEntity = produzioneRepo.save(produzioneEntity);
		
		//Registro lo scarico delle materie prime dal magazzino per far si che risultino occupate
		articoloService.scaricoDiProduzione(produzione.getQtaProdotta(), produzioneEntity.getScarichiDiProduzione());
		
		return produzioneEntity.getId();
	}
	
	/**
	 * Esegue la rettifica della quantità di un ordine di produzione, effettuando anche le correzioni necessarie agli articoli della distinta base
	 * @param produzione
	 */
	@Transactional
	public Long updateQta(Produzione produzione) {
		if (produzione.getId() == null) throw new NotAnUpdate();
		if (produzione.getQtaProdotta() <= 0) throw new ConstraintException("La quantità prodotta deve essere maggiore di zero.");
		if (produzione.getStato() != StatoProduzione.NON_AVVIATA) throw new BadRequestException("Questa produzione è già stata avviata, dunque non può essere modificata.");
		ProduzioneEntity produzioneEntity = produzioneRepo.findProduzioneById(produzione.getId()).orElseThrow(() -> new NotFoundException("ID '" + produzione.getId() + "'' was not found"));
		Double oldQta = produzioneEntity.getQtaProdotta();
		produzioneEntity.setQtaProdotta(produzione.getQtaProdotta());
		produzioneEntity = produzioneRepo.save(produzioneEntity);
		articoloService.modificaScaricoDiProduzione(oldQta, produzione.getQtaProdotta(), produzioneEntity.getScarichiDiProduzione());
		
		return produzioneEntity.getId();
	}
	
	/**
	 * Annulla un ordine di produzione
	 * @param id id ordine di produzione da annullare
	 */
	@Transactional
	public void annullaOrdineDiProduzioneById(Long id) {
		ProduzioneEntity produzioneEntity = produzioneRepo.findProduzioneById(id).orElseThrow(() -> new NotFoundException("ID '" + id + "'' was not found"));
		if (produzioneEntity.getStato() != StatoProduzione.NON_AVVIATA) throw new BadRequestException("Questa produzione è già stata avviata, dunque non può essere annullata.");
		articoloService.annullaScaricoDiProduzione(produzioneEntity.getQtaProdotta(), produzioneEntity.getScarichiDiProduzione());
		produzioneRepo.delete(produzioneEntity);
	}
	
	/**
	 * Setta lo stato dell'ordine di produzione ad "avviato", quasta funzione deve essere chiamata solo dal PLC Communication Server
	 * @param id id ordine di produzione da mettere in stato "avviato"
	 */
	@Transactional
	public void avviaProduzione(Long id) {
		ProduzioneEntity produzioneEntity = produzioneRepo.findProduzioneById(id).orElseThrow(() -> new NotFoundException("ID '" + id + "'' was not found"));
		if (produzioneEntity.getStato() != StatoProduzione.NON_AVVIATA) throw new BadRequestException("Questa produzione è già stata avviata.");
		produzioneEntity.setStato(StatoProduzione.AVVIATA);
		produzioneEntity.setInizioProduzione(LocalDateTime.now());
		produzioneRepo.save(produzioneEntity);
	}
	
	/**
	 * Setta lo stato della produzione a "completato", quasta funzione deve essere chiamata solo dal PLC Communication Server
	 * @param id id produzione da mettere in stato "completato"
	 */
	@Transactional
	public void terminaProduzione(Long id) {
		ProduzioneEntity produzioneEntity = produzioneRepo.findProduzioneById(id).orElseThrow(() -> new NotFoundException("ID '" + id + "'' was not found"));
		if (produzioneEntity.getStato() != StatoProduzione.AVVIATA) throw new BadRequestException("Questa produzione è da avviare o già completata.");
		produzioneEntity.setStato(StatoProduzione.COMPLETATA);
		produzioneEntity.setFineProduzione(LocalDateTime.now());
		produzioneEntity = produzioneRepo.save(produzioneEntity);
		articoloService.caricoDiProduzione(produzioneEntity.getQtaProdotta(), produzioneEntity.getArticoloProdotto());
	}
	
}
