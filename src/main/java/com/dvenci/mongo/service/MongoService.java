package com.dvenci.mongo.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dvenci.http.model.request.BinStats;
import com.dvenci.http.model.request.CompStats;
import com.dvenci.http.model.request.Correlation;
import com.dvenci.http.model.request.DescriptiveAnalysis;
import com.dvenci.http.model.request.FeatureEngineering;
import com.dvenci.http.model.request.NullCount;
import com.dvenci.http.model.request.PredictTarget;
import com.dvenci.http.model.request.PredictiveAnalysis;
import com.dvenci.http.model.request.Schema;
import com.dvenci.mongo.model.AnalysisHistory;

public interface MongoService {
	
	public AnalysisHistory createEntry(String username);
	public List<AnalysisHistory> getTimeStampsByUsername(String username);
	public AnalysisHistory getEntry(String id);
	
	public void saveSchema(Schema payload, JSONArray data);
	public void saveDescStats(DescriptiveAnalysis payload, JSONArray data);
	public void saveCorrelation(Correlation payload, JSONObject data);
	public void savePredictiveStats(PredictiveAnalysis payload, JSONArray data);
	public void saveFeatureEngineering(FeatureEngineering payload, JSONObject data);
	public void saveTargetPrediction(PredictTarget payload, JSONArray data);
	public void saveBinStats(BinStats payload, JSONObject data);
	public void saveNullCount(NullCount payload, JSONObject data);
	public void saveCompStats(CompStats payload, JSONArray data);
}
