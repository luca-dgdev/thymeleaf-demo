package com.example.thymeleafdemo.dao;

import java.io.Serializable;
import java.util.Set;

import com.example.thymeleafdemo.constants.AppRole;

public class GaeUser implements Serializable {
	private final String userId;
	private final String email;
	private final String nickname;
	private final String forename;
	private final String surname;
	private final Set<AppRole> authorities;
	private final boolean enabled;

	public GaeUser(String userId, String nickname, String email, String forename, String surname, Set<AppRole> roles,
			boolean userEnabled) {
		super();
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.forename = forename;
		this.surname = surname;
		this.authorities = roles;
		this.enabled = userEnabled;
	}

	public GaeUser(String userId, String nickname, String email) {
		super();
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.forename = "";
		this.surname = "";
		this.authorities = null;
		this.enabled = true;
	}

	public String getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}

	public Set<AppRole> getAuthorities() {
		return authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

}
