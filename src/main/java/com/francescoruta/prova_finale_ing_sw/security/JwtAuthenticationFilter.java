package com.francescoruta.prova_finale_ing_sw.security;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.francescoruta.prova_finale_ing_sw.entities.UserEntity;
import com.francescoruta.prova_finale_ing_sw.models.LoginInfo;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			try {
				JsonNode values = new ObjectMapper().readTree(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(values.get("username").asText(), values.get("password").asText());
				return authenticationManager.authenticate(authenticationToken);
			} catch (IOException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		UserEntity user = (UserEntity) authentication.getPrincipal();
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		LoginInfo loginInfo = jwtService.getNewTokenPair(user.getUsername(), roles, request.getRequestURL().toString());
		loginInfo.setRoles(String.join(",", roles));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), loginInfo);
	}
	
}
