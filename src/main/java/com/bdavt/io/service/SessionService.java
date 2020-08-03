package com.bdavt.io.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdavt.io.exception.httpClient.NotFoundException;
import com.bdavt.io.postgres.model.PySparkSession;
import com.bdavt.io.postgres.model.SparkSession;
import com.bdavt.io.postgres.repo.PySparkSessionRepo;
import com.bdavt.io.postgres.repo.SparkSessionRepo;

@Service
public class SessionService {

	public SessionService() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private LivyService livy;
	
//	@Autowired
//	private PostgresService postgresService;
	
	@Autowired
	private SparkSessionRepo sparkSession;
	
	@Autowired
	private PySparkSessionRepo pySparkSession;
	
	public Long getSparkSession(String username) throws Exception {
		System.out.println("Fetching spark session from db");
		SparkSession session = sparkSession.getSessionByUserName(username);
		Long sessionId = null;
		if(session == null) {
			System.out.println("No session found in db");
			sessionId = livy.createSession("spark");
			sparkSession.save(new SparkSession(username, sessionId));
			System.out.println("New session created");
		} else {
			sessionId = session.getSessionId();
			try {
				System.out.println("Validating session");
				String status = livy.checkSessionStatus(sessionId);
				if(status.equalsIgnoreCase("dead")) {
					System.out.println("Session dead");
					sessionId = livy.createSession("spark");
					System.out.println("New session created");
					session.setSessionId(sessionId);
					sparkSession.save(session);
				}
			} catch (NotFoundException e) {
				System.out.println("Invalid session from db");
				sessionId = livy.createSession("spark");
				System.out.println("New session created");
				session.setSessionId(sessionId);
				sparkSession.save(session);
			}
		}
		waitForStart(sessionId);
		return sessionId;
	}
	
	public Long getPySparkSession(String username) throws Exception {
		System.out.println("Fetching spark session from db");
		PySparkSession session = pySparkSession.getSessionByUserName(username);
		Long sessionId = null;
		if(session == null) {
			System.out.println("No session found in db");
			sessionId = livy.createSession("pyspark");
			pySparkSession.save(new PySparkSession(username, sessionId));
			System.out.println("New session created");
		} else {
			sessionId = session.getSessionId();
			try {
				System.out.println("Validating session");
				String status = livy.checkSessionStatus(sessionId);
				if(status.equalsIgnoreCase("dead")) {
					System.out.println("Session dead");
					sessionId = livy.createSession("pyspark");
					System.out.println("New session created");
					session.setSessionId(sessionId);
					pySparkSession.save(session);
				}
			} catch (NotFoundException e) {
				System.out.println("Invalid session from db");
				sessionId = livy.createSession("pyspark");
				System.out.println("New session created");
				session.setSessionId(sessionId);
				pySparkSession.save(session);
			}
		}
		waitForStart(sessionId);
		return sessionId;
	}
	
//	public Long getSession(String type, String username) throws Exception {
//		System.out.println("Fetching session from db");
//		Long sessionId = postgresService.getSessionId(type, username);
//		if(sessionId == null) {
//			System.out.println("No session found in db");
//			sessionId = livy.createSession(type);
//			postgresService.insertSessionId(type, username, sessionId);
//			System.out.println("New session created");
//		} else {
//			try {
//				System.out.println("Validating session");
//				String status = livy.checkSessionStatus(sessionId);
//				if(status.equalsIgnoreCase("dead")) {
//					System.out.println("Session dead");
//					sessionId = livy.createSession(type);
//					System.out.println("New session created");
//					postgresService.updateSessionId(type, username, sessionId);
//				}
//			} catch (NotFoundException e) {
//				System.out.println("Invalid session from db");
//				sessionId = livy.createSession(type);
//				System.out.println("New session created");
//				postgresService.updateSessionId(type, username, sessionId);
//			}
//		}
		
//		String status = livy.checkSessionStatus(sessionId);
//		System.out.println(status);
//		
//		while (!status.equalsIgnoreCase("idle")) {
//			System.out.println("Waiting for session to start");
//			TimeUnit.SECONDS.sleep(3);
//			status = livy.checkSessionStatus(sessionId);
//		}
//		waitForStart(sessionId);
//		return sessionId;
//	}
	
	public void waitForStart(Long sessionId) throws Exception {
		String status = livy.checkSessionStatus(sessionId);
		System.out.println(status);
		while (!status.equalsIgnoreCase("idle")) {
			System.out.println("Waiting for session to start");
			TimeUnit.SECONDS.sleep(3);
			status = livy.checkSessionStatus(sessionId);
		}
	}

}
