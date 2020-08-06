package com.dvenci.livy.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import com.dvenci.livy.exception.SessionNotFoundException;

public interface LivyService {
	
	public Long createSession(String type) throws ParseException, IOException;
	public JSONObject fetchSession(Long sessionId) throws ClientProtocolException, IOException, SessionNotFoundException, ParseException;
	public String checkSessionStatus(Long sessionId) throws ClientProtocolException, IOException, SessionNotFoundException, ParseException;
	public Long createStatement(Long sessionId, String code) throws IOException, ParseException;
	public JSONObject fetchStatement(Long sessionId, Long statementId) throws ClientProtocolException, IOException, ParseException;
	public String checkStatementStatus(Long sessionId, Long statementId) throws ClientProtocolException, IOException, ParseException;
	public JSONObject getStatementResponse(Long sessionId, Long statementId) throws InterruptedException, ClientProtocolException, IOException, ParseException;

}
