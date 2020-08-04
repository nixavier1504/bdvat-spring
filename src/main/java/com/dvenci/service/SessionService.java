package com.dvenci.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvenci.livy.exception.SessionNotFoundException;
import com.dvenci.livy.service.LivyService;
import com.dvenci.postgres.model.PySparkSession;
import com.dvenci.postgres.model.SparkSession;
import com.dvenci.postgres.repo.PySparkSessionRepo;
import com.dvenci.postgres.repo.SparkSessionRepo;

@Service
public class SessionService {

	public SessionService() {

	}
	
	@Autowired
	private LivyService livy;
	
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
			} catch (SessionNotFoundException e) {
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
			} catch (SessionNotFoundException e) {
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
