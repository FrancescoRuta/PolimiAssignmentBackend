package com.francescoruta.prova_finale_ing_sw.services;

import java.util.List;
import java.util.Optional;

import com.francescoruta.prova_finale_ing_sw.entities.*;
import com.francescoruta.prova_finale_ing_sw.exceptions.BadRequestException;
import com.francescoruta.prova_finale_ing_sw.exceptions.ConflictException;
import com.francescoruta.prova_finale_ing_sw.exceptions.NotAnUpdate;
import com.francescoruta.prova_finale_ing_sw.exceptions.NotFoundException;
import com.francescoruta.prova_finale_ing_sw.exceptions.UnauthorizedException;
import com.francescoruta.prova_finale_ing_sw.models.UserPasswordUpdate;
import com.francescoruta.prova_finale_ing_sw.models.UserNoPass;
import com.francescoruta.prova_finale_ing_sw.repos.UserRepo;
import com.francescoruta.prova_finale_ing_sw.utils.simplemapping.DefaultMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Optional<UserEntity> getByUsername(String username) {
		return userRepo.findUserByUsername(username);
	}
	
	public String[] getUserRoles(String username) {
		UserEntity user = userRepo.findUserByUsername(username).orElseThrow(() -> new NotFoundException("User '" + username + "' was not found"));
		return user.getRoles().split(",");
	}

	public Long add(UserEntity user) {
		user.setId(null);
		user.setIsSystem(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (getByUsername(user.getUsername()).isPresent()) throw new ConflictException("Questo username è già presente nel database.");
		if (user.getRoles().contains("PLC_SERVER")) throw new BadRequestException("Il ruolo PLC_SERVER non può essere assegnato.");
		return userRepo.save(user).getId();
	}

	public List<UserNoPass> getAll() {
		return DefaultMapper.mapCollection(UserNoPass.class, userRepo.findAllByIsSystem(false));
	}

	public Long update(UserEntity user) {
		user.setIsSystem(false);
		if (user.getId() == null) throw new NotAnUpdate();
		UserEntity oldUser = userRepo.findUserById(user.getId()).orElseThrow(() -> new NotFoundException("User " + user.getId() + " was not found"));
		if (oldUser.getIsSystem()) throw new BadRequestException("Impossibile effettuare questa operazione su un account di sistema");
		if (!oldUser.getUsername().equals(user.getUsername()) && getByUsername(user.getUsername()).isPresent()) throw new ConflictException("Questo username è già presente nel database.");
		if (user.getRoles().contains("PLC_SERVER")) throw new BadRequestException("Il ruolo PLC_SERVER non può essere assegnato.");
		user.setPassword(oldUser.getPassword());
		return userRepo.save(user).getId();
	}

	public Long updatePassword(UserEntity user) {
		user.setIsSystem(false);
		if (user.getId() == null) throw new NotAnUpdate();
		UserEntity oldUser = userRepo.findUserById(user.getId()).orElseThrow(() -> new NotFoundException("User " + user.getId() + " was not found"));
		if (oldUser.getIsSystem()) throw new BadRequestException("Impossibile effettuare questa operazione su un account di sistema");
		oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(oldUser).getId();
	}
	
	@Override
	public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
		return getByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}

	public void updateUserPassword(UserPasswordUpdate userPasswordUpdate) {
		UserEntity user = getByUsername(userPasswordUpdate.getUsername()).orElseThrow(() -> new UnauthorizedException("Username not found"));
		if (!passwordEncoder.matches(userPasswordUpdate.getOldPassword(), user.getPassword())) throw new UnauthorizedException("Wrong password");
		user.setPassword(passwordEncoder.encode(userPasswordUpdate.getNewPassword()));
		userRepo.save(user);
	}

	public void elimina(Long id) {
		UserEntity oldUser = userRepo.findUserById(id).orElseThrow(() -> new NotFoundException("User " + id + " was not found"));
		if (oldUser.getIsSystem()) throw new BadRequestException("Impossibile effettuare questa operazione su un account di sistema");
		userRepo.delete(oldUser);
	}
	
}
