package com.dvenci.repository;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisMethods {
	public JSONArray getSchema(Long sessionId, String dirPath) throws IOException, ParseException, InterruptedException;
	public JSONArray getDescriptiveAnalysis(Long sessionId, String dirPath, String cols) throws IOException, ParseException, InterruptedException;
	public JSONObject getCorrelation(Long sessionId, String dirPath, String cols) throws IOException, ParseException, InterruptedException;
	public JSONArray getPredictiveAnalysis(Long sessionId, String dirPath, String cols, int trainingPercent) throws IOException, ParseException, InterruptedException;
	public JSONObject getNullCount(Long sessionId, String dirPath, String col) throws IOException, ParseException, InterruptedException;
	public JSONObject getBinStats(Long sessionId, String dirPath, String col) throws IOException, ParseException, InterruptedException;
	public JSONArray getCompStats(Long sessionId, String dirPath1, String dirPath2, String col) throws IOException, ParseException, InterruptedException;
}
