package com.dvenci.repository;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public interface LivyMethods {
	public Long createSession(String type) throws Exception;
	public JSONObject fetchSession(Long sessionId) throws Exception;
	public String checkSessionStatus(Long sessionId) throws Exception;
	public Long createStatement(Long sessionId, String code) throws Exception;
	public JSONObject fetchStatement(Long sessionId, Long statementId) throws Exception;
	public String checkStatementStatus(Long sessionId, Long statementId) throws Exception;
}
