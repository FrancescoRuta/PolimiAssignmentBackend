package com.francescoruta.prova_finale_ing_sw.security;

import java.util.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.francescoruta.prova_finale_ing_sw.models.LoginInfo;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	private static final byte[] key = "CGPAWykS9NJm2&PZ3=#2Yx=eqKt6XZ7d".getBytes();
	
	private String createRefreshToken(String subject, String issuer) {
		return createRawToken(subject, issuer, null, 60 * 24);
	}
	private String createAccessToken(String subject, String issuer, List<String> roles) {
		return createRawToken(subject, issuer, roles, 10);
	}
	private String createRawToken(String subject, String issuer, List<String> roles, long expireInMinutes) {
		com.auth0.jwt.JWTCreator.Builder builder = JWT.create()
			.withSubject(subject)
			.withExpiresAt(new Date(System.currentTimeMillis() + expireInMinutes * 60 * 1000))
			.withIssuer(issuer);
		if (roles != null) {
			builder.withClaim("roles", roles);
		}
		return builder.sign(Algorithm.HMAC256(key));
	}
	
	public LoginInfo getNewTokenPair(String subject, List<String> roles, String issuer) {
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setAccessToken(createAccessToken(subject, issuer, roles));
		loginInfo.setRefreshToken(createRefreshToken(subject, issuer));
		loginInfo.setUsername(subject);
		return loginInfo;
	}
	
	public UsernamePasswordAuthenticationToken verifyToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(key);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		String username = decodedJWT.getSubject();
		String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Arrays.stream(roles).forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});
		return new UsernamePasswordAuthenticationToken(username, null, authorities);
	}
	
	public String getRefreshedAccessToken(String subject, List<String> roles, String issuer) {
		return createAccessToken(subject, issuer, roles);
	}
	
	public String getSubject(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(key);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT decodedJWT = verifier.verify(token);
			return decodedJWT.getSubject();
		} catch(Exception e) {
			return null;
		}
		
	}
	
}
