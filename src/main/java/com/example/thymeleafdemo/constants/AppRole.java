package com.example.thymeleafdemo.constants;

import org.springframework.security.core.GrantedAuthority;

public enum AppRole implements GrantedAuthority {
	ROLE_ADMIN(0), ROLE_NEW_USER(1), ROLE_USER(2), ROLE_PAG1(3);

	private int bit;

	AppRole(int bit) {
		this.bit = bit;
	}

	public String getAuthority() {
		return toString();
	}

	public int getBit() {
		return bit;
	}

}