package com.bdavt.io.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import scala.Array;

public class AnalysisUtils {

	@SuppressWarnings("unchecked")
	public static JSONArray processSchema(JSONObject schema) {
		Iterator<String> fields = schema.keySet().iterator();
		int i = 1;
		JSONArray schemaArray = new JSONArray();
		while(fields.hasNext()) {
			String field = fields.next();
			String dataType = (String)schema.get((String)field);
			JSONObject fieldObject = new JSONObject();
			fieldObject.put("fieldName", (String)field);
			fieldObject.put("dataType", dataType);
			fieldObject.put("id", i);
			fieldObject.put("selected", !dataType.equalsIgnoreCase("StringType"));
			schemaArray.add(fieldObject);
			i++;
		}
		return schemaArray;
	}
	
	public static String constructArray(JSONArray cols) {
		List<String> colList = new ArrayList<String>();
		for(Object col: cols) {
			colList.add("'" + (String) col + "'");
		}
		String columns = "[" + String.join(",", colList) + "]";
		return columns;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONArray processDescStats(JSONObject stats) {
		JSONArray statsArr = new JSONArray();
		JSONObject keyMap = (JSONObject) stats.get("summary");
		stats.remove("summary");
		for(Object field: stats.keySet()) {
			JSONObject data = (JSONObject) stats.get(field);
			JSONObject paramObj = new JSONObject();
			paramObj.put("field", field);
			for(Object param: data.keySet()) {
				paramObj.put(keyMap.get(param), data.get(param));
			}
			statsArr.add(paramObj);
		}
		return statsArr;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject processBinStats(JSONObject stats) {
		JSONObject statsObj = new JSONObject();
		for(Object key: stats.keySet()) {
			JSONObject valsObj = (JSONObject) stats.get(key);
			JSONArray valsArr = new JSONArray();
			for(Object i: valsObj.keySet()) {
				valsArr.add(valsObj.get(i));
			}
			if(!((String)key).equalsIgnoreCase("LeftEnd") && !((String)key).equalsIgnoreCase("RightEnd")) {
				statsObj.put("field", key);
				statsObj.put("bucketValues", valsArr);
			}
		}
		JSONObject leftEndObj = ((JSONObject)stats.get("LeftEnd"));
		JSONObject rightEndObj = ((JSONObject)stats.get("RightEnd"));
		JSONArray ranges = new JSONArray();
		for(Object key: leftEndObj.keySet()) {
			JSONObject rangeObj = new JSONObject();
			rangeObj.put("left", leftEndObj.get(key));
			rangeObj.put("right", rightEndObj.get(key));
			ranges.add(rangeObj);
		}
		statsObj.put("range", ranges);
		return statsObj;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject processNullCount(String field, Long count) {
		JSONObject responseObj = new JSONObject();
		responseObj.put("field", field);
		responseObj.put("nullCount", count);
		return responseObj;
	}
}
