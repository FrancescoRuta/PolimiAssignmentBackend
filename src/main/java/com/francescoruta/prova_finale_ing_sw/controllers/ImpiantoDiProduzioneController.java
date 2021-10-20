package com.francescoruta.prova_finale_ing_sw.controllers;

import java.util.List;

import com.francescoruta.prova_finale_ing_sw.models.*;
import com.francescoruta.prova_finale_ing_sw.services.ImpiantoDiProduzioneService;

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
@RequestMapping("/impiantoDiProduzione")
public class ImpiantoDiProduzioneController {
	private final ImpiantoDiProduzioneService impiantoDiProduzioneService;
	
	public ImpiantoDiProduzioneController(ImpiantoDiProduzioneService impiantoDiProduzioneService) {
		this.impiantoDiProduzioneService = impiantoDiProduzioneService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ImpiantoDiProduzione>> getAll() {
		return new ResponseEntity<>(impiantoDiProduzioneService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody ImpiantoDiProduzione data) {
		impiantoDiProduzioneService.add(data);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody ImpiantoDiProduzione data) {
		impiantoDiProduzioneService.update(data);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/elimina/{id}")
	public ResponseEntity<?> elimina(@PathVariable("id") Long id) {
		impiantoDiProduzioneService.elimina(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
