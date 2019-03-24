package com.example.thymeleafdemo.controller;

import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.thymeleafdemo.config.security.GaeUserAuthentication;
import com.example.thymeleafdemo.config.security.GoogleAccountsAuthenticationEntryPoint;
import com.example.thymeleafdemo.constants.AppRole;
import com.example.thymeleafdemo.dao.GaeUser;
import com.example.thymeleafdemo.dao.UserRegistry;
import com.example.thymeleafdemo.model.SimpleModel;
import com.google.appengine.api.users.UserServiceFactory;

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
	
	@GetMapping("/403")
	public String notPermittedPage(Model model) {
		return "403";
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public String register(@Valid RegistrationForm form, BindingResult result) {
        if (result.hasErrors()) {
            return null;
        }
        
        log.severe("form.forename: " + form.getForename());
        log.severe("form.surname: " + form.getSurname());
        
        log.severe("google user email: " + UserServiceFactory.getUserService().getCurrentUser().getEmail());
        log.severe("google userID: " + UserServiceFactory.getUserService().getCurrentUser().getUserId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GaeUser currentUser = (GaeUser)authentication.getPrincipal();
        Set<AppRole> roles = EnumSet.of(AppRole.USER);

        if (UserServiceFactory.getUserService().isUserAdmin()) {
            roles.add(AppRole.ADMIN);
        }

        GaeUser user = new GaeUser(currentUser.getUserId(), currentUser.getNickname(), currentUser.getEmail(),
                form.getForename(), form.getSurname(), roles, true);

        registry.registerUser(user);

        // Update the context with the full authentication
        SecurityContextHolder.getContext().setAuthentication(new GaeUserAuthentication(user, authentication.getDetails()));

        return "redirect:/index.html";
    }
}
