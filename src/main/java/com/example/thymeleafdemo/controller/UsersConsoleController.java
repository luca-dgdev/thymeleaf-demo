package com.example.thymeleafdemo.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.thymeleafdemo.dao.UserRegistry;


@Controller
public class UsersConsoleController {

	private static final Logger log = Logger.getLogger(UsersConsoleController.class.getName());
	
	@Autowired
	private UserRegistry registry;
	
	@GetMapping("/users_console")
	public String users_console(Model model) {

		return "users_console";
	}
}
