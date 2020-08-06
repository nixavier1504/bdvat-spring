package com.dvenci.service;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public interface AnalysisService {
	
	public JSONArray getSchema(Long sessionId, String dirPath) throws IOException, ParseException, InterruptedException;
	public JSONArray getDescriptiveAnalysis(Long sessionId, String dirPath, String cols) throws IOException, ParseException, InterruptedException;
	public JSONObject getCorrelation(Long sessionId, String dirPath, String cols) throws IOException, ParseException, InterruptedException;
	public JSONArray getPredictiveAnalysis(Long sessionId, String dirPath, String cols, int trainingPercent) throws IOException, ParseException, InterruptedException;
	public JSONObject getNullCount(Long sessionId, String dirPath, String col) throws IOException, ParseException, InterruptedException;
	public JSONObject getBinStats(Long sessionId, String dirPath, String col) throws IOException, ParseException, InterruptedException;
	public JSONArray getCompStats(Long sessionId, String dirPath1, String dirPath2, String col) throws IOException, ParseException, InterruptedException;
	public JSONObject getFeatureEngineering(Long sessionId, String dataset, String columns, String targetColumn, int thresholdPercentage)  throws IOException, ParseException, InterruptedException;
	public JSONArray getTargetPrediction(Long sessionId, String dataset, String columns, String targetColumn, int thresholdPercentage)  throws IOException, ParseException, InterruptedException;

}
