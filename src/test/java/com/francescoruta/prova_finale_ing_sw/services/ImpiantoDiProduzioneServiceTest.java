package com.francescoruta.prova_finale_ing_sw.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.*;

import com.francescoruta.prova_finale_ing_sw.models.ImpiantoDiProduzione;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ImpiantoDiProduzioneServiceTest {
	private final ImpiantoDiProduzioneService impiantoDiProduzioneService;
	
	@Autowired
	public ImpiantoDiProduzioneServiceTest(ImpiantoDiProduzioneService impiantoDiProduzioneService) {
		this.impiantoDiProduzioneService = impiantoDiProduzioneService;
	}
	
	@Test
	public void unitGetAll() {
		impiantoDiProduzioneService.getAll();
	}
	@Test
	public void getAll() {
		ImpiantoDiProduzione impiantoDiProduzione = new ImpiantoDiProduzione();
		impiantoDiProduzione.setNome("Test");
		impiantoDiProduzioneService.add(impiantoDiProduzione);
		List<ImpiantoDiProduzione> impiantiDiProduzione = impiantoDiProduzioneService.getAll();
		assert(impiantiDiProduzione.size() > 0);
	}
	@Test
	public void unitAdd() {
		ImpiantoDiProduzione impiantoDiProduzione = new ImpiantoDiProduzione();
		impiantoDiProduzione.setNome("Test");
		Long id = impiantoDiProduzioneService.add(impiantoDiProduzione);
		assertNotEquals(id, null);
	}
	@Test
	public void add() {
		ImpiantoDiProduzione impiantoDiProduzione = new ImpiantoDiProduzione();
		impiantoDiProduzione.setNome("Test");
		Long id = impiantoDiProduzioneService.add(impiantoDiProduzione);
		assertNotEquals(id, null);
		assert(impiantoDiProduzioneService.getAll().stream().filter(i -> i.getId().equals(id)).findAny().isPresent());
	}
	@Test
	public void update() {
		ImpiantoDiProduzione impiantoDiProduzione = new ImpiantoDiProduzione();
		impiantoDiProduzione.setNome("Test");
		Long id = impiantoDiProduzioneService.add(impiantoDiProduzione);
		impiantoDiProduzione.setId(id);
		impiantoDiProduzione.setNome("Test 1");
		impiantoDiProduzioneService.update(impiantoDiProduzione);
		ImpiantoDiProduzione updated = impiantoDiProduzioneService.getAll().stream().filter(i -> i.getId().equals(id)).findAny().get();
		assertEquals(impiantoDiProduzione.getNome(), updated.getNome());
	}
	@Test
	public void elimina() {
		ImpiantoDiProduzione impiantoDiProduzione = new ImpiantoDiProduzione();
		impiantoDiProduzione.setNome("Test");
		Long id = impiantoDiProduzioneService.add(impiantoDiProduzione);
		impiantoDiProduzioneService.elimina(id);
		assert(!impiantoDiProduzioneService.getAll().stream().filter(i -> i.getId().equals(id)).findAny().isPresent());
	}
	
}