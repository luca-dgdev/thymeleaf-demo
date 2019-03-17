package com.example.thymeleafdemo.controller;

import com.example.thymeleafdemo.validation.Forename;
import com.example.thymeleafdemo.validation.Surname;

public class RegistrationForm {
	@Forename
	private String forename;
	@Surname
	private String surname;

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}