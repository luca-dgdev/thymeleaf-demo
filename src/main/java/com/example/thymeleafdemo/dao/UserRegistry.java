package com.example.thymeleafdemo.dao;

public interface UserRegistry {
	GaeUser findUser(String userId);

	void registerUser(GaeUser newUser);

	void removeUser(String userId);
}