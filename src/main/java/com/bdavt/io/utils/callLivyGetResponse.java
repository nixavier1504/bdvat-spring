/**
 * Author: Sharad Jain
 * sharad_Jain
 * 2:39:13 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.bdavt.io.constants.AppConstants;

/**
 * @author sharad_Jain
 *
 */
public class callLivyGetResponse {
	public Long execStatement (String sessionId, String statementId) throws Exception {
		String content=null;
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httppost = new HttpGet("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionId+"/statements/"+statementId);
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
		Long stateId = (Long) json_object.get(AppConstants.ID);
		System.out.println("STatementid: " + stateId);
		return stateId;
	}
}
