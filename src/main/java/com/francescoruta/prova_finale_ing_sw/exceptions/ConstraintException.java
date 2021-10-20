package com.francescoruta.prova_finale_ing_sw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConstraintException extends ResponseStatusException {
	public ConstraintException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
