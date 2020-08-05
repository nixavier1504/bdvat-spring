package com.dvenci.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dvenci.http.model.request.ExecutionIdRequest;
import com.dvenci.http.model.request.Schema;
import com.dvenci.mongo.model.MHistory;
import com.dvenci.mongo.service.MongoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/history", produces = "application/json")
public class HistoryController {
	
	@Autowired
	private MongoService mongo;
	
	@PostMapping("/id")
	public MHistory getExecutionId(@RequestBody ExecutionIdRequest payload) {
		return mongo.createEntry(payload.getUsername());
	}
	
	@GetMapping("/{id}")
	public MHistory getHistoryById(@PathVariable("id") String id) {
		return mongo.getEntry(id);
	}
	
	@GetMapping("/list/{username}")
	public List<MHistory> getTimeStamps(@PathVariable("username") String username){
		return mongo.getTimeStampsByUsername(username);
	}
	
	@PostMapping("/test")
	public void test(@RequestBody Schema payload) {
		JSONArray data = new JSONArray();
		data.add("a");
		data.add("b");
		mongo.saveSchema(payload, data);
	}
}
