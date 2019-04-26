package com.example.thymeleafdemo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.thymeleafdemo.dao.GaeUser;
import com.googlecode.objectify.spring.ObjectifyFactoryBean;

@Configuration
public class ObjectifyConfig {

	@Bean
	public ObjectifyFactoryBean objectifyFactoryBean() {
		ObjectifyFactoryBean ofb = new ObjectifyFactoryBean();
		ofb.setClasses(Arrays.asList(GaeUser.class));
		return ofb;
	}

}
