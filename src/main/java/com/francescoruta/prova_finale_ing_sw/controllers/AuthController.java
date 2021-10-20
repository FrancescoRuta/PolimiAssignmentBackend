package com.francescoruta.prova_finale_ing_sw.controllers;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francescoruta.prova_finale_ing_sw.models.UserPasswordUpdate;
import com.francescoruta.prova_finale_ing_sw.models.LoginInfo;
import com.francescoruta.prova_finale_ing_sw.security.JwtService;
import com.francescoruta.prova_finale_ing_sw.services.UserService;
import com.francescoruta.prova_finale_ing_sw.exceptions.BadRequestException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
	private final JwtService jwtService;
	private final UserService userService;
	
	public AuthController(JwtService jwtService, UserService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
	@PutMapping("/updateUserPassword")
	public ResponseEntity<?> updateUserPassword(@RequestBody UserPasswordUpdate userPasswordUpdate) {
		userService.updateUserPassword(userPasswordUpdate);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/refresh")
	public ResponseEntity<LoginInfo> refresh(HttpServletRequest request, HttpServletResponse response) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorizationHeader != null) {
			try {
				String refreshToken = authorizationHeader;
				String subject = jwtService.getSubject(refreshToken);
				List<String> roles = Arrays.asList(userService.getUserRoles(subject));
				String accessToken = jwtService.getRefreshedAccessToken(subject, roles, request.getRequestURL().toString());
				LoginInfo loginInfo = new LoginInfo();
				loginInfo.setAccessToken(accessToken);
				loginInfo.setRefreshToken(refreshToken);
				loginInfo.setRoles(String.join(",", roles));
				loginInfo.setUsername(subject);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				return new ResponseEntity<>(loginInfo, HttpStatus.OK);
			} catch (Exception exception) {
				response.setHeader("error", exception.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				try {
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				} catch (IOException ioex) {}
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			throw new BadRequestException("Refresh token is missing");
		}
	}
	
}
