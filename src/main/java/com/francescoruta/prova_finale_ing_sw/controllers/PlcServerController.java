package com.francescoruta.prova_finale_ing_sw.controllers;

import java.util.List;

import com.francescoruta.prova_finale_ing_sw.models.*;
import com.francescoruta.prova_finale_ing_sw.services.ProduzioneService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plcServer")
public class PlcServerController {
	private final ProduzioneService produzioneService;
	
	public PlcServerController(ProduzioneService produzioneService) {
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
	
	@PatchMapping("/avviaProduzione/{id}")
	public ResponseEntity<?> avviaProduzione(@PathVariable("id") Long id) {
		produzioneService.avviaProduzione(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/terminaProduzione/{id}")
	public ResponseEntity<?> terminaProduzione(@PathVariable("id") Long id) {
		produzioneService.terminaProduzione(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
