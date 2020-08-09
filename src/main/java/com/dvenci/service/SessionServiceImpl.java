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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	private LivyService livy;
	
	@Autowired
	private SparkSessionRepo sparkSession;
	
	@Autowired
	private PySparkSessionRepo pySparkSession;
	
	@Override
	public Long getSparkSession(String username) throws Exception {
		SparkSession session = sparkSession.getSessionByUserName(username);
		Long sessionId = null;
		if(session == null) {
			sessionId = livy.createSession("spark");
			sparkSession.save(new SparkSession(username, sessionId));
			log.trace("New spark session created: " + sessionId);
		} else {
			sessionId = session.getSessionId();
			try {
				String status = livy.checkSessionStatus(sessionId);
				if(status.equalsIgnoreCase("dead")) {
					sessionId = livy.createSession("spark");
					log.trace("New spark session created: " + sessionId);
					session.setSessionId(sessionId);
					sparkSession.save(session);
				} else {
					log.trace("Using existing spark session: " + sessionId);
				}
			} catch (SessionNotFoundException e) {
				sessionId = livy.createSession("spark");
				log.trace("New spark session created: " + sessionId);
				session.setSessionId(sessionId);
				sparkSession.save(session);
			}
		}
		waitForStart(sessionId);
		return sessionId;
	}
	
	@Override
	public Long getPySparkSession(String username) throws Exception {
		PySparkSession session = pySparkSession.getSessionByUserName(username);
		Long sessionId = null;
		if(session == null) {
			sessionId = livy.createSession("pyspark");
			log.trace("New pyspark session created: " + sessionId);
			pySparkSession.save(new PySparkSession(username, sessionId));
		} else {
			sessionId = session.getSessionId();
			try {
				String status = livy.checkSessionStatus(sessionId);
				if(status.equalsIgnoreCase("dead")) {
					sessionId = livy.createSession("pyspark");
					log.trace("New pyspark session created: " + sessionId);
					session.setSessionId(sessionId);
					pySparkSession.save(session);
				} else {
					log.trace("Using existing pyspark session: " + sessionId);
				}
			} catch (SessionNotFoundException e) {
				sessionId = livy.createSession("pyspark");
				session.setSessionId(sessionId);
				pySparkSession.save(session);
			}
		}
		waitForStart(sessionId);
		return sessionId;
	}

	private void waitForStart(Long sessionId) throws Exception {
		String status = livy.checkSessionStatus(sessionId);
		while (!status.equalsIgnoreCase("idle")) {
			TimeUnit.SECONDS.sleep(3);
			status = livy.checkSessionStatus(sessionId);
		}
		log.trace("Session Idle: " + sessionId);
	}

}
