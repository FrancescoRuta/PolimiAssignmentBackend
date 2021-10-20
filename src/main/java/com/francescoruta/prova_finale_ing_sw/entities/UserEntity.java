package com.francescoruta.prova_finale_ing_sw.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class UserEntity implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	private String roles;
	private Boolean isSystem;
	
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return this.roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Boolean getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", username='" + getUsername() + "'" +
			", password='" + getPassword() + "'" +
			", roles='" + getRoles() + "'" +
			", isSystem='" + getIsSystem() + "'" +
			"}";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof UserEntity)) {
			return false;
		}
		UserEntity userEntity = (UserEntity) o;
		return Objects.equals(id, userEntity.id) && Objects.equals(username, userEntity.username) && Objects.equals(password, userEntity.password) && Objects.equals(roles, userEntity.roles) && isSystem == userEntity.isSystem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, roles, isSystem);
	}


	@Override
	public List<GrantedAuthority> getAuthorities() {
		return Arrays.stream(getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
