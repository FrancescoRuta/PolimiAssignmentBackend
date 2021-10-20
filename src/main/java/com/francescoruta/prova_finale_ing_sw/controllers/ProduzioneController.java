package com.francescoruta.prova_finale_ing_sw.controllers;

import java.util.List;

import com.francescoruta.prova_finale_ing_sw.models.*;
import com.francescoruta.prova_finale_ing_sw.services.ProduzioneService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produzione")
public class ProduzioneController {
	private final ProduzioneService produzioneService;
	
	public ProduzioneController(ProduzioneService produzioneService) {
		this.produzioneService = produzioneService;
	}
	
	@GetMapping("/ordini")
	public ResponseEntity<List<Produzione>> getOrdiniDiProduzione() {
		return new ResponseEntity<>(produzioneService.getOrdiniDiProduzione(), HttpStatus.OK);
	}
	
	@GetMapping("/inCorso")
	public ResponseEntity<List<Produzione>> getProduzioniInCorso() {
		return new ResponseEntity<>(produzioneService.getProduzioniInCorso(), HttpStatus.OK);
	}
	
	@GetMapping("/effettuate")
	public ResponseEntity<List<Produzione>> getProduzioniEffettuate() {
		return new ResponseEntity<>(produzioneService.getProduzioniEffettuate(), HttpStatus.OK);
	}
	
	@PostMapping("/addOrdine")
	public ResponseEntity<?> addOrdine(@RequestBody Produzione data) {
		produzioneService.addOrdine(data);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/updateQta")
	public ResponseEntity<?> updateQta(@RequestBody Produzione produzione) {
		produzioneService.updateQta(produzione);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/annullaOrdineDiProduzioneById/{id}")
	public ResponseEntity<?> annullaOrdineDiProduzioneById(@PathVariable("id") Long id) {
		produzioneService.annullaOrdineDiProduzioneById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
