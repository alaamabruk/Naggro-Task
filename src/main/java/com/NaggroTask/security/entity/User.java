package com.NaggroTask.security.entity;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



public class User {

	private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
	private String name;

	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	

	@NotBlank
	@Size(min = 6, max = 50)
	private String password;

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Set<Role> getRoles() {
		return roles;
	}



	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



	private Set<Role> roles = new HashSet<>();

	

	public User(String username, Set<Role> role, String password) {
		this.name = username;
		this.username = username;
		this.roles = role;
		this.password = password;
	}

}