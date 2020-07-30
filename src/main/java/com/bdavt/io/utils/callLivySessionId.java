/**
 * Author: Sharad Jain / Amit Kumar
 * 
 * 2:32:38 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bdavt.io.constants.AppConstants;

/**
 * @author Sharad_Jain / Amit Kumar
 *
 */
public class callLivySessionId {
	public Long getSessionID () throws Exception {
		   
		  HttpClient httpclient = HttpClients.createDefault();
		  HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/");
		  String content=null;
		  String json = "{\n\t\"kind\": \"spark\"\n}";
		  StringEntity entity = new StringEntity(json);
		    httppost.setEntity(entity);
		    httppost.setHeader("Accept", "application/json");
		    httppost.setHeader("Content-type", "application/json");
		    httppost.setHeader("X-Requested-By", "sharjain");
		  
		  try {
			    HttpResponse response = httpclient.execute(httppost);
			    HttpEntity respEntity = response.getEntity();

			    if (respEntity != null) {
			         content =  EntityUtils.toString(respEntity);
			        System.out.println(content);
			    }
			} catch (ClientProtocolException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			}
		  JSONParser parser = new JSONParser();
		  JSONObject json_object = (JSONObject) parser.parse(content);
		  Long sessionId = (Long) json_object.get(AppConstants.ID);

		  System.out.println("sessionId: " + sessionId);
	    return sessionId;
	}

}
