package com.example.thymeleafdemo.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.example.thymeleafdemo.dao.GaeUser;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyBootstrap implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ObjectifyService.init();
        ObjectifyService.register(GaeUser.class);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
