package com.pahanaedu.model;

import com.pahanaedu.model.enums.UserType;

public class User {
	private int id;
	private String name;
	private String username;
	private String passwordHash;
	private UserType userType;

	public User() {
	}

	public User(int id, String name, String username, String passwordHash, UserType userType) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.passwordHash = passwordHash;
		this.userType = userType;
	}

	public User(int id) {
		this.id = id;
	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
