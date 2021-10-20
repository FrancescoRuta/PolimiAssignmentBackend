package com.francescoruta.prova_finale_ing_sw.models;

import java.util.Objects;

public class UserNoPass {
	private Long id;
	private String username;
	private String roles;
	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoles() {
		return this.roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof UserNoPass)) {
			return false;
		}
		UserNoPass userNoPas = (UserNoPass) o;
		return Objects.equals(id, userNoPas.id) && Objects.equals(username, userNoPas.username) && Objects.equals(roles, userNoPas.roles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, roles);
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", username='" + getUsername() + "'" +
			", roles='" + getRoles() + "'" +
			"}";
	}

}
