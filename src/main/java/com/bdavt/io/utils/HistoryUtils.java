package com.bdavt.io.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HistoryUtils {
	
	@SuppressWarnings("unchecked")
	public static JSONArray strToJson(JSONArray strArr) {
		JSONArray op = new JSONArray();
		for(Object str: strArr) {
			try {
				op.add((JSONObject) new JSONParser().parse((String)str));
			} catch (ParseException e) {
				System.out.println("Exception");
				e.printStackTrace();
			}
		}
		return op;
	}
}
