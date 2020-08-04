package com.bdavt.io;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import org.json.simple.parser.ParseException; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BdavtPsqlSpringApplication {
	
	public static void main(String[] args) throws UnsupportedEncodingException, ClassNotFoundException, InterruptedException, SQLException, ParseException {
	    SpringApplication application = new SpringApplication(BdavtPsqlSpringApplication.class);
	    application.run(args);
	}

}
