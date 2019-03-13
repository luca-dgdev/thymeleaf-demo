package com.example.thymeleafdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.thymeleafdemo.model.SimpleModel;

@Controller
public class WebController {

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
}
