package com.dvenci.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	
	@SuppressWarnings("unchecked")
	public static JSONObject processFeatureEngineering(JSONArray stats) {
		JSONObject result = new JSONObject();
		result.put("targetVariable", (String)((JSONObject)stats.get(0)).get("idx"));
		JSONArray predictions = new JSONArray();
		
		JSONObject linearRegObj = (JSONObject)stats.get(0);
		linearRegObj.remove("idx");
		linearRegObj.put("rmse", (Double) linearRegObj.get("name"));
		linearRegObj.put("r2", (Double) linearRegObj.get("score"));
		linearRegObj.remove("name");
		linearRegObj.remove("score");
		linearRegObj.put("algorithm", "Linear Regression");
		predictions.add(linearRegObj);
		
		JSONObject randomForObj = (JSONObject)stats.get(1);
		randomForObj.remove("idx");
		randomForObj.remove("idx");
		randomForObj.put("rmse", (Double) randomForObj.get("name"));
		randomForObj.put("r2", (Double) randomForObj.get("score"));
		randomForObj.remove("name");
		randomForObj.remove("score");
		randomForObj.put("algorithm", "Random Forest");
		predictions.add(randomForObj);
		
		result.put("result", predictions);
		
		stats.remove(0);
		stats.remove(0);
		JSONArray featureImpArr = new JSONArray();
		for(Object featureImpObj: stats) {
			JSONObject featureImp = (JSONObject) featureImpObj;
			featureImp.put("field", (String) featureImp.get("name"));
			featureImp.remove("name");
			featureImp.remove("idx");
			featureImpArr.add(featureImp);
		}
		result.put("featureImportance", featureImpArr);
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public static JSONArray processTargetPrediction(JSONArray data, String targetVar) {
		JSONArray result = new JSONArray();
		for(Object obj : data) {
			JSONObject predictObj = (JSONObject) obj;
			predictObj.put("Predicted " + targetVar, predictObj.get("prediction"));
			predictObj.remove("prediction");
			result.add(predictObj);
		}
		return result;
	}
}
