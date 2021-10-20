package com.francescoruta.prova_finale_ing_sw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestException extends ResponseStatusException {
	public BadRequestException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
