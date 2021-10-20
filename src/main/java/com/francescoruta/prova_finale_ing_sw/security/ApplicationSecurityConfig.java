package com.francescoruta.prova_finale_ing_sw.security;

import java.util.*;

import com.francescoruta.prova_finale_ing_sw.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final JwtService jwtService;
	
	@Autowired
	public ApplicationSecurityConfig(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean(), jwtService);
		jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
		http.csrf().disable();
		http.cors().configurationSource(r -> corsConfiguration());
		http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/auth/**").permitAll();
		http.authorizeRequests().antMatchers("/produzione/**", "/articolo/allWithDistintaBase", "/impiantoDiProduzione/all").hasAnyAuthority("PRODUZIONE");
		http.authorizeRequests().antMatchers("/articolo/**").hasAnyAuthority("ARTICOLO");
		http.authorizeRequests().antMatchers("/utente/**").hasAnyAuthority("UTENTE");
		http.authorizeRequests().antMatchers("/impiantoDiProduzione/**").hasAnyAuthority("IMPIANTO_DI_PRODUZIONE");
		http.authorizeRequests().antMatchers("/plcServer/**").hasAnyAuthority("PLC_SERVER");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(jwtAuthenticationFilter);
		http.addFilterBefore(new JwtAuthorizationFilter(jwtService), JwtAuthenticationFilter.class);
		http.addFilterBefore(new JwtAuthorizationFilter(jwtService), JwtAuthenticationFilter.class);
	}
	
	public CorsConfiguration corsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		return corsConfiguration;
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		return userService;
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}