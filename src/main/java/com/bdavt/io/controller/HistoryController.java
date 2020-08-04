package com.bdavt.io.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdavt.io.model.request.UserRequest;
import com.bdavt.io.mongo.dao.HistoryDao;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/history", produces = "application/json")
public class HistoryController {
	
	@Autowired
	private HistoryDao dao;
	
	@PostMapping("/id")
	public JSONObject getExecutionId(@RequestBody UserRequest payload) throws Exception {
		return dao.createDocument(payload.getUsername());
	}
}
