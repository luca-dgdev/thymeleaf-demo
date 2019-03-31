package com.example.thymeleafdemo.config.security;

import java.util.Arrays;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private GoogleAccountsAuthenticationEntryPoint googleAccountsAuthenticationEntryPoint;

	@Autowired
	@Qualifier(value = "gaeAuthenticationProvider")
	private GoogleAccountsAuthenticationProvider googleAccountsAuthenticationProvider;

	private static final Logger log = Logger.getLogger(WebSecurityConfig.class.getName());

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		log.info("configure info");
		log.severe("configure severe");
		log.severe("googleAccountsAuthenticationEntryPoint: " + googleAccountsAuthenticationEntryPoint.toString());

		GaeAuthenticationFilter gaeAuthenticationFilter = new GaeAuthenticationFilter();
		gaeAuthenticationFilter.setAuthenticationManager(authenticationManager());

		// @formatter:off
		http
		.authorizeRequests().antMatchers("/", "/index.html", "/register.html").permitAll().and()
		.authorizeRequests().antMatchers("/pagina1.html").access("hasRole('ROLE_USER')").and()
		.authorizeRequests().antMatchers("/pagina2.html").access("hasRole('ROLE2')").and()
		.formLogin().permitAll().and()
		.logout().permitAll().and()
		.exceptionHandling().accessDeniedPage("/403.html").and()
		.exceptionHandling().authenticationEntryPoint(googleAccountsAuthenticationEntryPoint).and()
		.addFilterAt(gaeAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class);
		// @formatter:on
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(Arrays.asList(googleAccountsAuthenticationProvider));
	}

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 =
//             User.withDefaultPasswordEncoder()
//                .username("user1")
//                .password("password")
//                .roles("ROLE1")
//                .build();
//        
//        UserDetails user2 =
//                User.withDefaultPasswordEncoder()
//                   .username("user2")
//                   .password("password")
//                   .roles("ROLE2")
//                   .build();
//
//		return new InMemoryUserDetailsManager(user1, user2);
//	}
}