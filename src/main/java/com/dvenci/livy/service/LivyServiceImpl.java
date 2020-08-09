package com.dvenci.livy.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dvenci.livy.exception.SessionNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LivyServiceImpl implements LivyService {
	
	@Value("http://${livy.host}:${livy.port}")
	private String LIVY_BASEPATH;
	
	@SuppressWarnings("unchecked")
	@Override
	public Long createSession(String type) throws ParseException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(LIVY_BASEPATH + "/sessions");
		JSONObject payload = new JSONObject();
		payload.put("kind", type);
		StringEntity entity1 = new StringEntity(payload.toJSONString());
		httpPost.setEntity(entity1);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("X-Requested-By", "dvenci");
		
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity respEntity = response.getEntity();
		if (respEntity != null) {
			String responseStr =  EntityUtils.toString(respEntity);
			JSONObject responseJson = (JSONObject) new JSONParser().parse(responseStr);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				return (Long) responseJson.get("id");
			}
		}
		return null;
	}

	@Override
	public JSONObject fetchSession(Long sessionId) throws ClientProtocolException, IOException, SessionNotFoundException, ParseException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(LIVY_BASEPATH + "/sessions/" + sessionId);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("X-Requested-By", "dvenci");
		HttpResponse response = httpclient.execute(httpGet);
	    HttpEntity respEntity = response.getEntity();
	    
	    //Session Not found
	    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
	    	throw new SessionNotFoundException();
	    }
	    if (respEntity != null) {
	    	JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(EntityUtils.toString(respEntity));
	        return json;
	    } else {
	    	return new JSONObject();
	    }
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long createStatement(Long sessionId, String code) throws IOException, ParseException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(LIVY_BASEPATH + "/sessions/"+sessionId+"/statements");
		JSONObject payload = new JSONObject();
		payload.put("code", code);
		StringEntity entity = new StringEntity(payload.toJSONString());
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("X-Requested-By", "dvenci");

		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity respEntity = response.getEntity();

		if (respEntity != null) {
			String responseStr =  EntityUtils.toString(respEntity);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				Long statementId = (Long)((JSONObject) new JSONParser().parse(responseStr)).get("id");
				log.trace("Created statement with Id: " + statementId);
				return statementId;
			}
		}
		return null;
	}
	
	@Override
	public JSONObject fetchStatement(Long sessionId, Long statementId) throws ClientProtocolException, IOException, ParseException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(LIVY_BASEPATH + "/sessions/" + sessionId + "/statements/" + statementId);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		httpGet.setHeader("X-Requested-By", "dvenci");
		  
	    HttpResponse response = httpClient.execute(httpGet);
	    HttpEntity respEntity = response.getEntity();

	    if (respEntity != null) {
	        String responseStr =  EntityUtils.toString(respEntity);
	        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	        	return (JSONObject) new JSONParser().parse(responseStr);
	        }
	    }
	    
		return null;
	}

	@Override
	public String checkStatementStatus(Long sessionId, Long statementId) throws ClientProtocolException, IOException, ParseException {
		JSONObject json = fetchStatement(sessionId, statementId);
		if(json != null) {
			return (String) json.get("state");
		}
		return null;
	}

	@Override
	public String checkSessionStatus(Long sessionId) throws ClientProtocolException, IOException, SessionNotFoundException, ParseException {
		String state = (String) fetchSession(sessionId).get("state");
		return state;
	}
	
	@Override
	public JSONObject getStatementResponse(Long sessionId, Long statementId) throws InterruptedException, ClientProtocolException, IOException, ParseException {
		String status = "";
		log.trace("Executing statement");
		while(!status.equalsIgnoreCase("available")) {
			TimeUnit.SECONDS.sleep(1);
			status = checkStatementStatus(sessionId, statementId);
		}
		JSONObject response = fetchStatement(sessionId, statementId);
		log.trace("Statement executed");
		return response;
	}
	
}
