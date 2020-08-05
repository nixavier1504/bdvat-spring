package com.dvenci.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dvenci.http.model.request.BinStats;
import com.dvenci.http.model.request.CompStats;
import com.dvenci.http.model.request.Correlation;
import com.dvenci.http.model.request.DescriptiveAnalysis;
import com.dvenci.http.model.request.FeatureEngineering;
import com.dvenci.http.model.request.NullCount;
import com.dvenci.http.model.request.PredictTarget;
import com.dvenci.http.model.request.PredictiveAnalysis;
import com.dvenci.http.model.request.Schema;
import com.dvenci.mongo.service.MongoService;
import com.dvenci.service.AnalysisService;
import com.dvenci.service.SessionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/livy", produces = "application/json")
public class LivyController {
	
	@Autowired
	private SessionService session;
	
	@Autowired
	private AnalysisService analysis;
	
	@Autowired
	private MongoService mongo;
	
	@PostMapping("/schema")
	public JSONArray getSchema(@RequestBody Schema payload) throws Exception {
		Long sessionId = session.getSparkSession(payload.getUsername());
		JSONArray schema = analysis.getSchema(sessionId, payload.getDataset());
		mongo.saveSchema(payload, schema);
		return schema;
	}
	
	@PostMapping("/stats")
	public JSONArray getDescriptiveAnalysis(@RequestBody DescriptiveAnalysis payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONArray stats = analysis.getDescriptiveAnalysis(sessionId, payload.getDataset(), payload.getColumns());
		mongo.saveDescStats(payload, stats);
		return stats;
	}
	
	@PostMapping("/correlation")
	public JSONObject getCorrelation(@RequestBody Correlation payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONObject stats = analysis.getCorrelation(sessionId, payload.getDataset(), payload.getColumns());
		mongo.saveCorrelation(payload, stats);
		return stats;
	}
	
	@PostMapping("/predictive")
	public JSONArray getPrecitiveAnalysis(@RequestBody PredictiveAnalysis payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONArray stats = analysis.getPredictiveAnalysis(sessionId, payload.getDataset(), payload.getColumns(), payload.getThresholdPercentage());
		mongo.savePredictiveStats(payload, stats);
		return stats;
	}
	
	@PostMapping("/nullcount")
	public JSONObject getNullCount(@RequestBody NullCount payload) throws Exception {
		Long sessionId = session.getSparkSession(payload.getUsername());
		JSONObject stats = analysis.getNullCount(sessionId, payload.getDataset(), payload.getColumn());
		mongo.saveNullCount(payload, stats);
		return stats;
	}
	
	@PostMapping("/binstats")
	public JSONObject getBinStats(@RequestBody BinStats payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONObject stats = analysis.getBinStats(sessionId, payload.getDataset(), payload.getColumns());
		mongo.saveBinStats(payload, stats);
		return stats;
	}
	
	@PostMapping("/compstats")
	public JSONArray getCompStats(@RequestBody CompStats payload) throws Exception {
		Long sessionId = session.getSparkSession(payload.getUsername());
		JSONArray stats = analysis.getCompStats(sessionId, payload.getDataset1(), payload.getDataset2(), payload.getColumn());
		mongo.saveCompStats(payload, stats);
		return stats;
	}
	
	@PostMapping("/featureEngineering")
	public JSONObject getFeatureEngineering(@RequestBody FeatureEngineering payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONObject stats = analysis.getFeatureEngineering(sessionId, payload.getDataset(), payload.getColumns(), payload.getTargetColumn(), payload.getThresholdPercentage());
		mongo.saveFeatureEngineering(payload, stats);
		return stats;
	}
	
	@PostMapping("/predictTarget")
	public JSONObject getTargetPrediction(@RequestBody PredictTarget payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONObject stats = analysis.getTargetPrediction(sessionId, payload.getDataset(), payload.getColumns(), payload.getTargetColumn(), payload.getThresholdPercentage());
		mongo.saveTargetPrediction(payload, stats);
		return stats;
	}
}
