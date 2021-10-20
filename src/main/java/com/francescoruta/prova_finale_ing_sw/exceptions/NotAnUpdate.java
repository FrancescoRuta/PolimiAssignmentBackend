package com.francescoruta.prova_finale_ing_sw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotAnUpdate extends ResponseStatusException {
	public NotAnUpdate() {
		super(HttpStatus.BAD_REQUEST);
	}
	public NotAnUpdate(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
