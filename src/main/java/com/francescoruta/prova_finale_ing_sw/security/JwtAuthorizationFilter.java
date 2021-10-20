package com.francescoruta.prova_finale_ing_sw.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	
	public JwtAuthorizationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if(request.getServletPath().startsWith("/auth/")) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authorizationHeader != null) {
				try {
					String token = authorizationHeader;
					UsernamePasswordAuthenticationToken authenticationToken = jwtService.verifyToken(token);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}catch (Exception exception) {
					response.setHeader("error", exception.getMessage());
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					Map<String, String> error = new HashMap<>();
					error.put("errorMessage", exception.getMessage());
					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
				filterChain.doFilter(request, response);
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}
}