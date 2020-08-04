package com.dvenci.controller;

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

import com.dvenci.model.request.ExecutionIdRequest;
import com.dvenci.mongo.dao.HistoryDao;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/history", produces = "application/json")
public class HistoryController {
	
	@Autowired
	private HistoryDao dao;
	
	@PostMapping("/id")
	public JSONObject getExecutionId(@RequestBody ExecutionIdRequest payload) throws Exception {
		return dao.createDocument(payload.getUsername());
	}
	
	@GetMapping("")
	public JSONArray getHistoryList() {
		return dao.getTimeStamps("user1");
	}
	
	@GetMapping("/{_id}")
	public JSONObject getHistoryById(@PathVariable("_id") String _id) {
		return dao.getHistoryById(_id);
	}
}
