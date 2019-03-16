package com.example.thymeleafdemo;

import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThymeleafDemoApplication implements CommandLineRunner {

	private static final Logger log = Logger.getLogger(ThymeleafDemoApplication.class.getName());
	
	public static void main(String[] args) {
		SpringApplication.run(ThymeleafDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("ThymeleafDemoApplication started");
		
	}

}
