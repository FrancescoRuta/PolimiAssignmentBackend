package com.francescoruta.prova_finale_ing_sw.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.francescoruta.prova_finale_ing_sw.entities.UserEntity;
import com.francescoruta.prova_finale_ing_sw.models.UserNoPass;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
	private final UserService userService;
	
	@Autowired
	public UserServiceTest(UserService userService) {
		this.userService = userService;
	}
	
	@Test
	public void unitGetAll() {
		userService.getAll();
	}
	
	@Test
	public void getAll() {
		UserEntity user = new UserEntity();
		user.setUsername("Test 0 - getAll");
		user.setPassword("Test 1");
		user.setRoles("Test 2");
		Long id = userService.add(user);
		userService.getAll();
		userService.elimina(id);
	}
	
	@Test
	public void unitAdd() {
		UserEntity user = new UserEntity();
		user.setUsername("Test 0 - unitAdd");
		user.setPassword("Test 1");
		user.setRoles("Test 2");
		Long id = userService.add(user);
		assertNotEquals(id, null);
		userService.elimina(id);
	}
	
	@Test
	public void add() {
		UserEntity user = new UserEntity();
		user.setUsername("Test 0 - add");
		user.setPassword("Test 1");
		user.setRoles("Test 2");
		Long id = userService.add(user);
		assertNotEquals(id, null);
		assert(userService.getAll().stream().filter(i -> i.getId().equals(id)).findAny().isPresent());
		userService.elimina(id);
	}
	
	@Test
	public void update() {
		UserEntity user = new UserEntity();
		user.setUsername("Test 0 - update");
		user.setPassword("Test 1");
		user.setRoles("Test 2");
		Long id = userService.add(user);
		user.setId(id);
		user.setUsername("UpdateTest 0");
		user.setPassword("UpdateTest 1");
		user.setRoles("UpdateTest 2");
		userService.update(user);
		UserNoPass updated = userService.getAll().stream().filter(i -> i.getId().equals(id)).findAny().get();
		assertEquals(user.getUsername(), updated.getUsername());
		assertEquals(user.getRoles(), updated.getRoles());
		userService.elimina(id);
	}
	
	@Test
	public void updatePassword() {
		UserEntity user = new UserEntity();
		user.setUsername("Test 0 - updatePassword");
		user.setPassword("Test 1");
		user.setRoles("Test 2");
		Long id = userService.add(user);
		user.setId(id);
		user.setPassword("UpdateTest 1");
		userService.updatePassword(user);
		userService.elimina(id);
	}
	
	@Test
	public void elimina() {
		UserEntity user = new UserEntity();
		user.setUsername("Test 0 - elimina");
		user.setPassword("Test 1");
		user.setRoles("Test 2");
		Long id = userService.add(user);
		userService.elimina(id);
		assert(!userService.getAll().stream().filter(i -> i.getId().equals(id)).findAny().isPresent());
	}
	
}
