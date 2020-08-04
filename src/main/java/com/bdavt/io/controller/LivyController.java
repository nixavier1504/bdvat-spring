package com.bdavt.io.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdavt.io.model.request.BinStats;
import com.bdavt.io.model.request.CompStats;
import com.bdavt.io.model.request.Correlation;
import com.bdavt.io.model.request.DescriptiveAnalysis;
import com.bdavt.io.model.request.NullCount;
import com.bdavt.io.model.request.PredictiveAnalysis;
import com.bdavt.io.model.request.Schema;
import com.bdavt.io.mongo.dao.HistoryDao;
import com.bdavt.io.service.AnalysisService;
import com.bdavt.io.service.SessionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/livy", consumes = "application/json", produces = "application/json")
public class LivyController {
	
	@Autowired
	private SessionService session;
	
	@Autowired
	private AnalysisService analysis;
	
	@Autowired
	private HistoryDao history;
	
	@PostMapping("/schema")
	public JSONArray getSchema(@RequestBody Schema payload) throws Exception {
		Long sessionId = session.getSparkSession(payload.getUsername());
		JSONArray schema = analysis.getSchema(sessionId, payload.getDataset());
		history.saveSchema(payload, schema);
		return schema;
	}
	
	@PostMapping("/stats")
	public JSONArray getDescriptiveAnalysis(@RequestBody DescriptiveAnalysis payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONArray stats = analysis.getDescriptiveAnalysis(sessionId, payload.getDataset(), payload.getColumns());
		history.saveDescriptiveStats(payload, stats);
		return stats;
	}
	
	@PostMapping("/correlation")
	public JSONObject getCorrelation(@RequestBody Correlation payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONObject stats = analysis.getCorrelation(sessionId, payload.getDataset(), payload.getColumns());
		history.saveCorrelation(payload, stats);
		return stats;
	}
	
	@PostMapping("/predictive")
	public JSONArray getPrecitiveAnalysis(@RequestBody PredictiveAnalysis payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONArray stats = analysis.getPredictiveAnalysis(sessionId, payload.getDataset(), payload.getColumns(), payload.getThresholdPercantage());
		history.savePredictiveStats(payload, stats);
		return stats;
	}
	
	@PostMapping("/nullcount")
	public JSONObject getNullCount(@RequestBody NullCount payload) throws Exception {
		Long sessionId = session.getSparkSession(payload.getUsername());
		JSONObject stats = analysis.getNullCount(sessionId, payload.getDataset(), payload.getColumn());
		history.saveNullCount(payload, stats);
		return stats;
	}
	
	@PostMapping("/binstats")
	public JSONObject getBinStats(@RequestBody BinStats payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		JSONObject stats = analysis.getBinStats(sessionId, payload.getDataset(), payload.getColumns());
		history.saveBinStats(payload, stats);
		return stats;
	}
	
	@PostMapping("/compstats")
	public JSONArray getCompStats(@RequestBody CompStats payload) throws Exception {
		Long sessionId = session.getSparkSession(payload.getUsername());
		JSONArray stats = analysis.getCompStats(sessionId, payload.getDataset1(), payload.getDataset2(), payload.getColumn());
		history.saveCompStats(payload, stats);
		return stats;
	}
}
