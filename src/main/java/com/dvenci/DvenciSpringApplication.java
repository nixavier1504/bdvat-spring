package com.dvenci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DvenciSpringApplication {
	
	public static void main(String[] args) {
	    SpringApplication application = new SpringApplication(DvenciSpringApplication.class);
	    application.run(args);
	}

}
