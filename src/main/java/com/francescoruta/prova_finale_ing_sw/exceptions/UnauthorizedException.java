package com.francescoruta.prova_finale_ing_sw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {
	public UnauthorizedException() {
		super(HttpStatus.UNAUTHORIZED);
	}
	public UnauthorizedException(String message) {
		super(HttpStatus.UNAUTHORIZED, message);
	}
}
