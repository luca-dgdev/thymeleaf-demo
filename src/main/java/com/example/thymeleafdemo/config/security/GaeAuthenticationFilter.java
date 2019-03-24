package com.example.thymeleafdemo.config.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import com.example.thymeleafdemo.constants.AppRole;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class GaeAuthenticationFilter extends GenericFilterBean {

	private static final Logger log = Logger.getLogger(GaeAuthenticationFilter.class.getName());

	private static final String REGISTRATION_URL = "/register.html";
	private AuthenticationDetailsSource ads = new WebAuthenticationDetailsSource();

	private AuthenticationManager authenticationManager;
	private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// PARTE A OGNI RICHIESTA
		log.severe("GaeAuthenticationFilter.doFilter");
		log.severe("GaeAuthenticationFilter.authenticationManager: " + authenticationManager);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			// User isn't authenticated. Check if there is a Google Accounts user
			log.severe("User isn't authenticated. Check if there is a Google Accounts user");
			User googleUser = UserServiceFactory.getUserService().getCurrentUser();

			if (googleUser != null) {
				log.severe(
						"User has returned after authenticating through GAE. Need to authenticate to Spring Security.");
				log.severe("Google user.email: " + googleUser.getEmail() + " user.id: " + googleUser.getUserId());
				// User has returned after authenticating through GAE. Need to authenticate to
				// Spring Security.
				PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(googleUser, null);
				token.setDetails(ads.buildDetails(request));

				try {
					authentication = authenticationManager.authenticate(token);
					// Setup the security context
					log.severe("Setup the security context");
					SecurityContextHolder.getContext().setAuthentication(authentication);
					// Send new users to the registration page.
					if (authentication.getAuthorities().contains(AppRole.ROLE_NEW_USER)) {
						log.severe("Send new users to the registration page.");
						((HttpServletResponse) response).sendRedirect(REGISTRATION_URL);
						return;
					}
				} catch (AuthenticationException e) {
					log.severe("Authentication information was rejected by the authentication manager");
					// Authentication information was rejected by the authentication manager
					failureHandler.onAuthenticationFailure((HttpServletRequest) request, (HttpServletResponse) response,
							e);
					return;
				}
			} else {
				log.severe("googleUser is null");
			}
		} else {
			log.severe("authentication is not null. Principal :" + authentication.getPrincipal());
		}

		chain.doFilter(request, response);
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
		this.failureHandler = failureHandler;
	}
}