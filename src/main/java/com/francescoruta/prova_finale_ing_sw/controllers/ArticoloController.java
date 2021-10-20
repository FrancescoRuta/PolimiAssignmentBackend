package com.francescoruta.prova_finale_ing_sw.controllers;

import java.util.List;

import com.francescoruta.prova_finale_ing_sw.models.*;
import com.francescoruta.prova_finale_ing_sw.services.ArticoloQtaUpdateService;
import com.francescoruta.prova_finale_ing_sw.services.ArticoloService;

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
@RequestMapping("/articolo")
public class ArticoloController {
	private final ArticoloService articoloService;
	private final ArticoloQtaUpdateService articoloQtaUpdateService;
	
	public ArticoloController(ArticoloService articoloService, ArticoloQtaUpdateService articoloQtaUpdateService) {
		this.articoloService = articoloService;
		this.articoloQtaUpdateService = articoloQtaUpdateService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Articolo>> getAll() {
		return new ResponseEntity<>(articoloService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/movimenti")
	public ResponseEntity<List<ArticoloQtaUpdate>> movimenti() {
		return new ResponseEntity<>(articoloQtaUpdateService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/allWithDistintaBase")
	public ResponseEntity<List<Articolo>> getAllWithDistintaBase() {
		return new ResponseEntity<>(articoloService.getAllWithDistintaBase(), HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Articolo> getById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(articoloService.getById(id), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Articolo data) {
		articoloService.add(data);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Articolo data) {
		articoloService.update(data);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/rettificaGiacenza/{id}")
	public ResponseEntity<?> rettificaGiacenza(@PathVariable("id") Long id, @RequestBody Double qta) {
		articoloService.rettificaGiacenza(id, qta);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/elimina/{id}")
	public ResponseEntity<?> elimina(@PathVariable("id") Long id) {
		articoloService.elimina(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
