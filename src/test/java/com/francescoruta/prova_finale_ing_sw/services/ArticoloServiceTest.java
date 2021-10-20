package com.francescoruta.prova_finale_ing_sw.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import com.francescoruta.prova_finale_ing_sw.exceptions.NotFoundException;
import com.francescoruta.prova_finale_ing_sw.models.Articolo;
import com.francescoruta.prova_finale_ing_sw.models.DistintaBase;
import com.francescoruta.prova_finale_ing_sw.models.UnitaDiMisura;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@SpringBootTest
public class ArticoloServiceTest {
	private final ArticoloService articoloService;
	
	@Autowired
	public ArticoloServiceTest(ArticoloService articoloService) {
		this.articoloService = articoloService;
	}
	
	@Test
	public void unitAdd() {
		Articolo articolo = new Articolo();
		articolo.setCodice("Test 0");
		articolo.setDescrizione("Test 1");
		articolo.setUnitaDiMisura(UnitaDiMisura.KG);
		articolo.setDescrizioneEstesa("Test 2");
		articolo.setDistinteBase(new ArrayList<>());
		Long id = articoloService.add(articolo);
		assert(id != null);
	}
	
	@Test
	public void add() {
		Articolo articolo = new Articolo();
		articolo.setCodice("Test 0");
		articolo.setDescrizione("Test 1");
		articolo.setUnitaDiMisura(UnitaDiMisura.KG);
		articolo.setDescrizioneEstesa("Test 2");
		articolo.setDistinteBase(new ArrayList<>());
		Long id = articoloService.add(articolo);
		assert(id != null);
		Articolo inserito = articoloService.getById(id);
		assertEquals(articolo.getCodice(), inserito.getCodice());
		assertEquals(articolo.getDescrizione(), inserito.getDescrizione());
		assertEquals(articolo.getUnitaDiMisura(), inserito.getUnitaDiMisura());
		assertEquals(articolo.getDescrizioneEstesa(), inserito.getDescrizioneEstesa());
	}
	@Test
	public void unitGetAll() {
		articoloService.getAll();
	}
	@Test
	public void getAll() {
		Articolo articolo = new Articolo();
		articolo.setCodice("Test");
		articolo.setDescrizione("Test");
		articolo.setUnitaDiMisura(UnitaDiMisura.KG);
		articolo.setDescrizioneEstesa("Test");
		articolo.setDistinteBase(new ArrayList<>());
		articoloService.add(articolo);
		List<Articolo> articoli = articoloService.getAll();
		assert(articoli.size() > 0);
	}
	@Test
	public void unitGetAllWithDistintaBase() {
		articoloService.getAllWithDistintaBase();
	}
	@Test
	public void getAllWithDistintaBase() {
		Articolo articolo = new Articolo();
		articolo.setCodice("Test");
		articolo.setDescrizione("Test");
		articolo.setUnitaDiMisura(UnitaDiMisura.KG);
		articolo.setDescrizioneEstesa("Test");
		DistintaBase distintaBase = new DistintaBase();
		distintaBase.setDescrizione("Distinta di test");
		distintaBase.setArticoliDistintaBase(new ArrayList<>());
		articolo.setDistinteBase(Collections.singletonList(distintaBase));
		articoloService.add(articolo);
		
		List<Articolo> articoliWithDistintaBase = articoloService.getAllWithDistintaBase();
		assert(articoliWithDistintaBase.stream().filter(a -> a.getDistinteBase() != null && a.getDistinteBase().size() > 0).findAny().isPresent());
	}
	@Test
	public void update() {
		Articolo articolo = new Articolo();
		articolo.setCodice("Test 0");
		articolo.setDescrizione("Test 1");
		articolo.setUnitaDiMisura(UnitaDiMisura.KG);
		articolo.setDescrizioneEstesa("Test 3");
		articolo.setDistinteBase(new ArrayList<>());
		articolo.setId(articoloService.add(articolo));
		
		articolo.setCodice("UpdateTest 0");
		articolo.setDescrizione("UpdateTest 1");
		articolo.setUnitaDiMisura(UnitaDiMisura.NR);
		articolo.setDescrizioneEstesa("UpdateTest 3");
		Long id = articoloService.update(articolo);
		assertEquals(id, articolo.getId());
		
		Articolo modificato = articoloService.getById(id);
		assertEquals(articolo.getCodice(), modificato.getCodice());
		assertEquals(articolo.getDescrizione(), modificato.getDescrizione());
		assertEquals(articolo.getUnitaDiMisura(), modificato.getUnitaDiMisura());
		assertEquals(articolo.getDescrizioneEstesa(), modificato.getDescrizioneEstesa());
	}
	@Test
	public void rettificaGiacenza() {
		Articolo articolo = new Articolo();
		articolo.setCodice("Test 0");
		articolo.setDescrizione("Test 1");
		articolo.setUnitaDiMisura(UnitaDiMisura.KG);
		articolo.setDescrizioneEstesa("Test 3");
		articolo.setDistinteBase(new ArrayList<>());
		Long id = articoloService.add(articolo);
		Double qtaGiacenza = articoloService.getById(id).getQtaGiacenza();
		articoloService.rettificaGiacenza(id, qtaGiacenza + 1);
		Double qtaGiacenzaRettificata = articoloService.getById(id).getQtaGiacenza();
		assertEquals(qtaGiacenza + 1, qtaGiacenzaRettificata);
	}
	@Test
	public void elimina() {
		Articolo articolo = new Articolo();
		articolo.setCodice("Test 0");
		articolo.setDescrizione("Test 1");
		articolo.setUnitaDiMisura(UnitaDiMisura.KG);
		articolo.setDescrizioneEstesa("Test 3");
		articolo.setDistinteBase(new ArrayList<>());
		Long id = articoloService.add(articolo);
		articoloService.elimina(id);
		try {
			articoloService.getById(id);
			assert(false);
		} catch (NotFoundException ex) { }
	}
	
}
