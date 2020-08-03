package com.bdavt.io.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdavt.io.exception.httpClient.NotFoundException;

@Service
public class SessionService {

	public SessionService() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private LivyService livy;
	
	@Autowired
	private PostgresService postgresService;
	
	public Long getSession(String type, String username) throws Exception {
		System.out.println("Fetching session from db");
		Long sessionId = postgresService.getSessionId(type, username);
		if(sessionId == null) {
			System.out.println("No session found in db");
			sessionId = livy.createSession(type);
			postgresService.insertSessionId(type, username, sessionId);
			System.out.println("New session created");
		} else {
			try {
				System.out.println("Validating session");
				String status = livy.checkSessionStatus(sessionId);
				if(status.equalsIgnoreCase("dead")) {
					System.out.println("Session dead");
					sessionId = livy.createSession(type);
					System.out.println("New session created");
					postgresService.updateSessionId(type, username, sessionId);
				}
			} catch (NotFoundException e) {
				System.out.println("Invalid session from db");
				sessionId = livy.createSession(type);
				System.out.println("New session created");
				postgresService.updateSessionId(type, username, sessionId);
			}
		}
		
		String status = livy.checkSessionStatus(sessionId);
		System.out.println(status);
		
		while (!status.equalsIgnoreCase("idle")) {
			System.out.println("Waiting for session to start");
			TimeUnit.SECONDS.sleep(3);
			status = livy.checkSessionStatus(sessionId);
		}
		return sessionId;
	}

}
