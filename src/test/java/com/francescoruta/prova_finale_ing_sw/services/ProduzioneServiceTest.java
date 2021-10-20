package com.francescoruta.prova_finale_ing_sw.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import com.francescoruta.prova_finale_ing_sw.models.Articolo;
import com.francescoruta.prova_finale_ing_sw.models.ArticoloDistintaBase;
import com.francescoruta.prova_finale_ing_sw.models.DistintaBase;
import com.francescoruta.prova_finale_ing_sw.models.ImpiantoDiProduzione;
import com.francescoruta.prova_finale_ing_sw.models.Produzione;
import com.francescoruta.prova_finale_ing_sw.models.UnitaDiMisura;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
public class ProduzioneServiceTest {
	private final ProduzioneService produzioneService;
	private final ArticoloService articoloService;
	private final ImpiantoDiProduzioneService impiantoDiProduzioneService;
	
	@Autowired
	public ProduzioneServiceTest(ProduzioneService produzioneService, ArticoloService articoloService, ImpiantoDiProduzioneService impiantoDiProduzioneService) {
		this.produzioneService = produzioneService;
		this.articoloService = articoloService;
		this.impiantoDiProduzioneService = impiantoDiProduzioneService;
	}
	
	private Articolo newArticolo(int i) {
		Articolo articolo = new Articolo();
		articolo.setCodice("Test " + i + "0");
		articolo.setDescrizione("Test " + i + "1");
		articolo.setUnitaDiMisura(UnitaDiMisura.KG);
		articolo.setDescrizioneEstesa("Test " + i + "3");
		articolo.setDistinteBase(new ArrayList<>());
		Long id = articoloService.add(articolo);
		return articoloService.getAllWithDistintaBase().stream().filter(a -> a.getId().equals(id)).findAny().get();
	}
	
	private ImpiantoDiProduzione newImpiantoDiProduzione() {
		ImpiantoDiProduzione impiantoDiProduzione = new ImpiantoDiProduzione();
		impiantoDiProduzione.setNome("Test");
		Long id = impiantoDiProduzioneService.add(impiantoDiProduzione);
		return impiantoDiProduzioneService.getAll().stream().filter(a -> a.getId().equals(id)).findAny().get();
	}
	
	private Articolo newArticoloWithDistinta(Articolo articoloInDistinta) {
		Articolo articolo2 = newArticolo(1);
		DistintaBase distintaBase = new DistintaBase();
		ArticoloDistintaBase articoloDistintaBase = new ArticoloDistintaBase();
		distintaBase.setDescrizione("Distinta di test");
		distintaBase.setArticoliDistintaBase(Collections.singletonList(articoloDistintaBase));
		articoloDistintaBase.setArticolo(articoloInDistinta);
		articoloDistintaBase.setQta(1.0);
		articolo2.setDistinteBase(Collections.singletonList(distintaBase));
		articoloService.update(articolo2);
		return articoloService.getAllWithDistintaBase().stream().filter(a -> a.getId().equals(articolo2.getId())).findAny().get();
	}
	
	private Produzione newOrdineDiProduzione(Articolo articoloInDistinta) {
		Articolo articolo = newArticoloWithDistinta(articoloInDistinta);
		return newOrdineDiProduzioneFromArticoloDaProdurre(articolo);
	}
	
	private Produzione newOrdineDiProduzioneFromArticoloDaProdurre(Articolo articolo) {
		ImpiantoDiProduzione impiantoDiProduzione = newImpiantoDiProduzione();
		
		Produzione produzione = new Produzione();
		produzione.setQtaProdotta(1.0);
		produzione.setArticoloProdotto(articolo);
		produzione.setImpiantoDiProduzione(impiantoDiProduzione);
		produzione.setDistintaBase(articolo.getDistinteBase().get(0));
		Long id = produzioneService.addOrdine(produzione);
		return produzioneService.getOrdiniDiProduzione().stream().filter(o -> o.getId().equals(id)).findAny().get();
	}
	
	private Produzione newOrdineDiProduzione() {
		return newOrdineDiProduzione(newArticolo(0));
	}
	
	@Test
	public void addOrdine() {
		newOrdineDiProduzione();
	}

	@Test
	public void unitGetOrdiniDiProduzione() {
		produzioneService.getOrdiniDiProduzione();
	}
	
	@Test
	public void unitGetProduzioniInCorso() {
		produzioneService.getProduzioniInCorso();
	}
	
	@Test
	public void unitGetProduzioniEffettuate() {
		produzioneService.getProduzioniEffettuate();
	}
	
	@Test
	public void getOrdiniDiProduzione() {
		newOrdineDiProduzione();
		List<Produzione> list = produzioneService.getOrdiniDiProduzione();
		assert(list.size() > 0);
	}
	
	@Test
	public void getProduzioniInCorso() {
		Produzione produzione = newOrdineDiProduzione();
		produzioneService.avviaProduzione(produzione.getId());
		List<Produzione> list = produzioneService.getProduzioniInCorso();
		assert(list.size() > 0);
	}
	
	@Test
	public void getProduzioniEffettuate() {
		Produzione produzione = newOrdineDiProduzione();
		produzioneService.avviaProduzione(produzione.getId());
		produzioneService.terminaProduzione(produzione.getId());
		List<Produzione> list = produzioneService.getProduzioniEffettuate();
		assert(list.size() > 0);
	}
	
	@Test
	public void updateQta() {
		Produzione produzione = newOrdineDiProduzione();
		Double newQta = 2.0;
		produzione.setQtaProdotta(newQta);
		Long id = produzioneService.updateQta(produzione);
		Double qta = produzioneService.getOrdiniDiProduzione().stream().filter(o -> o.getId().equals(id)).findAny().get().getQtaProdotta();
		assertEquals(qta, newQta);
	}
	
	@Test
	public void annullaOrdineDiProduzioneById() {
		produzioneService.annullaOrdineDiProduzioneById(newOrdineDiProduzione().getId());
	}
	
	@Test
	public void avviaProduzione() {
		Produzione produzione = newOrdineDiProduzione();
		produzioneService.avviaProduzione(produzione.getId());
	}
	
	@Test
	public void terminaProduzione() {
		Produzione produzione = newOrdineDiProduzione();
		produzioneService.avviaProduzione(produzione.getId());
		produzioneService.terminaProduzione(produzione.getId());
	}
	
	@Test
	public void checkScaricoDiMagazzino() {
		Articolo articoloInDistinta = newArticolo(0);
		Double oldQtaGiacenza = articoloInDistinta.getQtaGiacenza();
		newOrdineDiProduzione(articoloInDistinta);
		assertEquals(oldQtaGiacenza - 1, articoloService.getById(articoloInDistinta.getId()).getQtaGiacenza());
	}
	
	@Test
	public void checkQtaUpdate() {
		Articolo articoloInDistinta = newArticolo(0);
		Double oldQtaGiacenza = articoloInDistinta.getQtaGiacenza();
		Produzione produzione = newOrdineDiProduzione(articoloInDistinta);
		produzione.setQtaProdotta(2.0);
		produzioneService.updateQta(produzione);
		assertEquals(oldQtaGiacenza - 2, articoloService.getById(articoloInDistinta.getId()).getQtaGiacenza());
	}
	
	@Test
	public void checkAnnulla() {
		Articolo articoloInDistinta = newArticolo(0);
		Double oldQtaGiacenza = articoloInDistinta.getQtaGiacenza();
		Produzione produzione = newOrdineDiProduzione(articoloInDistinta);
		produzioneService.annullaOrdineDiProduzioneById(produzione.getId());
		assertEquals(oldQtaGiacenza, articoloService.getById(articoloInDistinta.getId()).getQtaGiacenza());
	}
	
	@Test
	public void checkTermina() {
		Articolo articoloDaProdurre = newArticoloWithDistinta(newArticolo(0));
		Double oldQtaGiacenza = articoloDaProdurre.getQtaGiacenza();
		Produzione produzione = newOrdineDiProduzioneFromArticoloDaProdurre(articoloDaProdurre);
		produzioneService.avviaProduzione(produzione.getId());
		produzioneService.terminaProduzione(produzione.getId());
		assertEquals(oldQtaGiacenza + 1, articoloService.getById(articoloDaProdurre.getId()).getQtaGiacenza());
	}
	
}
