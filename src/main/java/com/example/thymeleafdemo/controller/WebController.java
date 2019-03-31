package com.example.thymeleafdemo.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.thymeleafdemo.dao.UserRegistry;
import com.example.thymeleafdemo.model.SimpleModel;
import com.example.thymeleafdemo.model.UserRegistration;

@Controller
public class WebController {

	private static final Logger log = Logger.getLogger(WebController.class.getName());

	@Autowired
	private UserRegistry registry;

//	@RequestMapping(value = { "/" })
//	public String index() {
//		return "index";
//	}

	@GetMapping("/")
	public String greeting(Model model) {
		model.addAttribute("simpleModel", new SimpleModel(1L, "pippo"));
		model.addAttribute("nome", "paperino");
		return "index";
	}

	@GetMapping("/pagina1")
	public String pagina1(Model model) {
		return "pagina1";
	}

	@GetMapping("/pagina2")
	public String pagina2(Model model) {
		return "pagina2";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("userRegistration", new UserRegistration());
		return "register";
	}

	@GetMapping("/403")
	public String notPermittedPage(Model model) {
		return "403";
	}

}
