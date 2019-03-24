package com.example.thymeleafdemo.config.security;

import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.example.thymeleafdemo.dao.GaeUser;
import com.example.thymeleafdemo.dao.UserRegistry;
import com.google.appengine.api.users.User;

public class GoogleAccountsAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger log = Logger.getLogger(GoogleAccountsAuthenticationProvider.class.getName());
	
	private UserRegistry userRegistry;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// NON PARTE
		log.severe("GoogleAccountsAuthenticationProvider.authenticate");
		
		User googleUser = (User) authentication.getPrincipal();
		log.severe("GoogleAccountsAuthenticationProvider.googleUser: " + googleUser);
		log.severe("GoogleAccountsAuthenticationProvider.userRegistry: " + userRegistry);

		GaeUser user = userRegistry.findUser(googleUser.getUserId());

		if (user == null) {
			// User not in registry. Needs to register
			user = new GaeUser(googleUser.getUserId(), googleUser.getNickname(), googleUser.getEmail());
		}

		if (!user.isEnabled()) {
			throw new DisabledException("Account is disabled");
		}

		return new GaeUserAuthentication(user, authentication.getDetails());
	}

	public final boolean supports(Class<?> authentication) {
		log.severe("GoogleAccountsAuthenticationProvider.supports");
		return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public void setUserRegistry(UserRegistry userRegistry) {
		this.userRegistry = userRegistry;
	}
}