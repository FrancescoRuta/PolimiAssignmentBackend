package com.francescoruta.prova_finale_ing_sw.models;

import java.util.Objects;

public class LoginInfo {
	private String accessToken;
	private String refreshToken;
	private String username;
	private String roles;

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getRoles() {
		return this.roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public String toString() {
		return "{" +
			" username='" + getUsername() + "'" +
			", accessToken='" + getAccessToken() + "'" +
			", refreshToken='" + getRefreshToken() + "'" +
			", roles='" + getRoles() + "'" +
			"}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof LoginInfo)) {
			return false;
		}
		LoginInfo loginInfo = (LoginInfo) o;
		return Objects.equals(accessToken, loginInfo.accessToken) && Objects.equals(refreshToken, loginInfo.refreshToken) && Objects.equals(roles, loginInfo.roles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessToken, refreshToken, roles);
	}
	
}
