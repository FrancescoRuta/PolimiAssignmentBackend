package com.francescoruta.prova_finale_ing_sw.models;

import java.io.Serializable;
import java.util.Objects;

public class UserPasswordUpdate implements Serializable {
	private String username;
	private String oldPassword;
	private String newPassword;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return this.oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return this.newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof UserPasswordUpdate)) {
			return false;
		}
		UserPasswordUpdate currentUserPasswordUpdate = (UserPasswordUpdate) o;
		return Objects.equals(oldPassword, currentUserPasswordUpdate.oldPassword) && Objects.equals(newPassword, currentUserPasswordUpdate.newPassword);
	}

	@Override
	public int hashCode() {
		return Objects.hash(oldPassword, newPassword);
	}

	@Override
	public String toString() {
		return "{" +
			" username='" + getUsername() + "'" +
			", oldPassword='" + getOldPassword() + "'" +
			", newPassword='" + getNewPassword() + "'" +
			"}";
	}
	
	
}
