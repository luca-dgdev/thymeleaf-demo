package com.example.thymeleafdemo.controller;

import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Profiles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.thymeleafdemo.config.security.GaeUserAuthentication;
import com.example.thymeleafdemo.constants.AppRole;
import com.example.thymeleafdemo.dao.GaeUser;
import com.example.thymeleafdemo.dao.UserRegistry;
import com.example.thymeleafdemo.model.UserRegistration;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
@RequestMapping(value = "/registeruser")
public class RegistrationController {
	
	private static final Logger log = Logger.getLogger(RegistrationController.class.getName());

	@Autowired
	private UserRegistry registry;
	
	@Autowired
	private ConfigurableEnvironment env;

	@RequestMapping(method = RequestMethod.POST)
	public String register(final UserRegistration userRegistration) {
		
		log.severe("RegistrationController.register");

        log.severe("form.forename: " + userRegistration.getForename());
        log.severe("form.surname: " + userRegistration.getSurname());
        
        String email;
        String userId;
        GaeUser currentUser;
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<AppRole> roles = EnumSet.of(AppRole.ROLE_USER, AppRole.ROLE1);
        
        if(env.acceptsProfiles(Profiles.of("dev")))
        {
        	log.severe("dev profile -> activating stub values");
        	email = "test@thymeleaf-demo.com";
        	userId = "1";
        	currentUser = new GaeUser(userId, null, email, null, null, null, true);
        }
        else
        {
        	log.severe("prod profile");
        	email = UserServiceFactory.getUserService().getCurrentUser().getEmail();
        	userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();
        	currentUser = (GaeUser) authentication.getPrincipal();
        	if (UserServiceFactory.getUserService().isUserAdmin()) {
    			roles.add(AppRole.ROLE_ADMIN);
    		}
        }
        
        log.severe("google user email: " + email);
        log.severe("google userID: " + userId);

		GaeUser user = new GaeUser(currentUser.getUserId(), currentUser.getNickname(), currentUser.getEmail(),
				userRegistration.getForename(), userRegistration.getSurname(), roles, true);

		registry.registerUser(user);

		// Update the context with the full authentication
		log.severe("Update the context with the full authentication");
		SecurityContextHolder.getContext()
				.setAuthentication(new GaeUserAuthentication(user, authentication.getDetails()));

		return "index";
	}
}