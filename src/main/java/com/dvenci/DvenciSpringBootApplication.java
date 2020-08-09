package com.dvenci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DvenciSpringBootApplication {
	
	public static void main(String[] args) {
	    SpringApplication application = new SpringApplication(DvenciSpringBootApplication.class);
	    application.run(args);
	}

}
