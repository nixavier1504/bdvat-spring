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
import com.bdavt.io.service.AnalysisService;
import com.bdavt.io.service.SessionService;

@RestController
@RequestMapping(value = "/livy", consumes = "application/json", produces = "application/json")
public class LivyController {
	
	@Autowired
	private SessionService session;
	
	@Autowired
	private AnalysisService analysis;
	
	@CrossOrigin(origins = "*")
	@PostMapping("/schema")
	public JSONArray getSchema(@RequestBody Schema payload) throws Exception {
		Long sessionId = session.getSparkSession(payload.getUsername());
		return analysis.getSchema(sessionId, payload.getDataset());
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/stats")
	public JSONArray getDescriptiveAnalysis(@RequestBody DescriptiveAnalysis payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		return analysis.getDescriptiveAnalysis(sessionId, payload.getDataset(), payload.getColumns());
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/correlation")
	public JSONObject getCorrelation(@RequestBody Correlation payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		return analysis.getCorrelation(sessionId, payload.getDataset(), payload.getColumns());
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/predictive")
	public JSONArray getPrecitiveAnalysis(@RequestBody PredictiveAnalysis payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		return analysis.getPredictiveAnalysis(sessionId, payload.getDataset(), payload.getColumns(), payload.getThresholdPercantage());
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/nullcount")
	public JSONObject getNullCount(@RequestBody NullCount payload) throws Exception {
		Long sessionId = session.getSparkSession(payload.getUsername());
		return analysis.getNullCount(sessionId, payload.getDataset(), payload.getColumn());
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/binstats")
	public JSONObject getBinStats(@RequestBody BinStats payload) throws Exception {
		Long sessionId = session.getPySparkSession(payload.getUsername());
		return analysis.getBinStats(sessionId, payload.getDataset(), payload.getColumns());
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/compstats")
	public JSONArray getCompStats(@RequestBody CompStats payload) throws Exception {
		Long sessionId = session.getSparkSession(payload.getUsername());
		return analysis.getCompStats(sessionId, payload.getDataset1(), payload.getDataset2(), payload.getColumn());
	}
}
