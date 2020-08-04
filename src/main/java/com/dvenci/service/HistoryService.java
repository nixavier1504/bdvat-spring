package com.dvenci.service;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvenci.http.model.request.Schema;
import com.dvenci.mongo.model.History;
import com.dvenci.mongo.repo.HistoryDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Service
public class HistoryService {
	
	@Autowired
	private HistoryDao dao;

	@SuppressWarnings("unchecked")
	public JSONObject createDocument(String username) throws ParseException {
		History history = new History();
		history.init();
		history.setUsername(username);
		DBObject doc = dao.saveDocument(history.getDbObject());
		String jsonstr = JSON.serialize(doc);
		JSONObject docJson = (JSONObject) new JSONParser().parse(jsonstr);
		JSONObject exeuctionJson = new JSONObject();
		exeuctionJson.put("executionId", ((JSONObject)docJson.get("_id")).get("$oid"));
		return exeuctionJson;
	}
	
	@SuppressWarnings("unchecked")
	public void saveSchema(Schema payload, JSONArray schema) {
		String executionId = payload.getExecutionId();
		JSONObject schemaEntry = new JSONObject();
		schemaEntry.put("dataset", payload.getDataset());
		schemaEntry.put("profile", payload.getProfile());
		schemaEntry.put("username", payload.getUsername());
		schemaEntry.put("schema", schema);
		
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(executionId));
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("schema", schemaEntry.toJSONString());
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$push", newDocument);
		
		dao.updateDocument(query, updateObject);
	}
}
