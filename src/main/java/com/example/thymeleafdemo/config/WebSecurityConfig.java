package com.example.thymeleafdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private GoogleAccountsAuthenticationEntryPoint googleAccountsAuthenticationEntryPoint;
    
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
        http
            .authorizeRequests().antMatchers("/", "/index.html").permitAll()
            .and()
            .authorizeRequests().antMatchers("/pagina1.html").access("hasRole('ROLE1')")
            .and()
            .authorizeRequests().antMatchers("/pagina2.html").access("hasRole('ROLE2')")
//            .and()
//            .formLogin().permitAll()
//            .and()
//            .logout().permitAll()
//            .and()
//            .exceptionHandling().accessDeniedPage("/403.html")
            .and()
            .exceptionHandling().authenticationEntryPoint(googleAccountsAuthenticationEntryPoint)
            ;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user1 =
             User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password")
                .roles("ROLE1")
                .build();
        
        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                   .username("user2")
                   .password("password")
                   .roles("ROLE2")
                   .build();

		return new InMemoryUserDetailsManager(user1, user2);
	}
}