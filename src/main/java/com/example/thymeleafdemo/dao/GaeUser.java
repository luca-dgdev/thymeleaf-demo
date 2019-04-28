package com.example.thymeleafdemo.dao;

import java.io.Serializable;
import java.util.EnumSet;

import com.example.thymeleafdemo.constants.AppRole;
import com.example.thymeleafdemo.utils.Utils;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class GaeUser implements Serializable{
	@Id
	String userId;
	String email;
	String nickname;
	String forename;
	String surname;
	long binaryAuthorities;
	boolean enabled;

	public GaeUser() {
		super();
	}

	public GaeUser(String userId, String nickname, String email, String forename, String surname, long binaryAuthorities,
			boolean userEnabled) {
		super();
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.forename = forename;
		this.surname = surname;
		this.binaryAuthorities = binaryAuthorities;
		this.enabled = userEnabled;
	}

	public GaeUser(String userId, String nickname, String email) {
		super();
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.forename = "";
		this.surname = "";
		this.binaryAuthorities = Utils.calculateBinaryAuthorities(EnumSet.of(AppRole.ROLE_NEW_USER));
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

	public void setBinaryAuthorities(long binaryAuthorities) {
		this.binaryAuthorities = binaryAuthorities;
	}

	public long getBinaryAuthorities() {
		return binaryAuthorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

}
