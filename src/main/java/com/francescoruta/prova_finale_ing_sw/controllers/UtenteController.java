package com.francescoruta.prova_finale_ing_sw.controllers;

import java.util.List;

import com.francescoruta.prova_finale_ing_sw.entities.UserEntity;
import com.francescoruta.prova_finale_ing_sw.models.UserNoPass;
import com.francescoruta.prova_finale_ing_sw.services.UserService;

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
@RequestMapping("/utente")
public class UtenteController {
	private final UserService userService;
	
	public UtenteController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<UserNoPass>> getAll() {
		return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody UserEntity user) {
		userService.add(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody UserEntity user) {
		userService.update(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(@RequestBody UserEntity user) {
		userService.updatePassword(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/elimina/{id}")
	public ResponseEntity<?> elimina(@PathVariable("id") Long id) {
		userService.elimina(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
