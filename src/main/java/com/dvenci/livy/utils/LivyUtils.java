package com.dvenci.livy.utils;

import org.json.simple.JSONArray;
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
				Object genericResponse = ((JSONObject) output.get("data")).get("application/json");
				if(genericResponse.getClass() == JSONArray.class) {
					genericResponse = ((JSONArray) genericResponse).toJSONString();
				}
				if(genericResponse.getClass() == JSONObject.class) {
					genericResponse = ((JSONObject) genericResponse).toJSONString();
				}
				String jsonStr = (String) genericResponse;
				return new JSONParser().parse(jsonStr);
			}
		}
		return json;
	}
	
}
