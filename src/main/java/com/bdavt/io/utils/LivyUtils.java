package com.bdavt.io.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;

public class LivyUtils {
	
	@Value("${livy.basepath}")
	private static String LIVY_BASEPATH;
	
	public static Object getResultFromResponse(JSONObject response) throws ParseException {
		JSONObject output = (JSONObject)response.get("output");
		JSONObject json = new JSONObject();
		if(output != null) {
			if("ok".equalsIgnoreCase((String) output.get("status"))) {
				String jsonStr = (String) ((JSONObject) output.get("data")).get("application/json");
				return new JSONParser().parse(jsonStr);
			}
		}
		return json;
	}
	
//	public static JSONObject livyGet(String path) throws ClientProtocolException, IOException, ParseException {
//		HttpClient httpClient = HttpClients.createDefault();
//		HttpGet httpGet = new HttpGet(LIVY_BASEPATH + path);
//		httpGet.setHeader("Accept", "application/json");
//		httpGet.setHeader("Content-type", "application/json");
//		httpGet.setHeader("X-Requested-By", "dvenci");
//		  
//	    HttpResponse response = httpClient.execute(httpGet);
//	    HttpEntity respEntity = response.getEntity();
//
//	    if (respEntity != null) {
//	        String responseStr =  EntityUtils.toString(respEntity);
//	        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//	        	return (JSONObject) new JSONParser().parse(responseStr);
//	        }
//	    }
//		return null;
//	}
//	
//	public static JSONObject livyPost(String path, JSONObject payload) throws ClientProtocolException, IOException, ParseException {
//		HttpClient httpClient = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost(LIVY_BASEPATH + path);
//		StringEntity entity1 = new StringEntity(payload.toJSONString());
//		httpPost.setEntity(entity1);
//		httpPost.setHeader("Accept", "application/json");
//		httpPost.setHeader("Content-type", "application/json");
//		httpPost.setHeader("X-Requested-By", "dvenci");
//		
//		HttpResponse response = httpClient.execute(httpPost);
//		HttpEntity respEntity = response.getEntity();
//		if (respEntity != null) {
//			String responseStr =  EntityUtils.toString(respEntity);
//			return (JSONObject) new JSONParser().parse(responseStr);
//			
//		}
//		return null;
//	}
	
}
