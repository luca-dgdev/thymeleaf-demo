package com.example.thymeleafdemo.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Profiles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.thymeleafdemo.dao.GaeUser;
import com.example.thymeleafdemo.dao.UserRegistry;
import com.example.thymeleafdemo.model.UserRegistration;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class WebController {

	private static final Logger log = Logger.getLogger(WebController.class.getName());

	@Autowired
	private UserRegistry registry;

	@Autowired
	private ConfigurableEnvironment env;

	@GetMapping("/")
	public String greeting(Model model) {
		String nickname = "NON AUTENTICATO";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null
				&& authentication.getPrincipal() instanceof GaeUser) {
			GaeUser principal = (GaeUser) authentication.getPrincipal();
			nickname = principal.getNickname();
		}
		model.addAttribute("nickname", nickname);
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

		if (env.acceptsProfiles(Profiles.of("!dev")) && UserServiceFactory.getUserService().getCurrentUser() == null) {
			return "403";
		}

		return "register";
	}

	@GetMapping("/403")
	public String notPermittedPage(Model model) {
		return "403";
	}

}
