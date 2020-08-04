package com.bdavt.io.mongo.dao;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdavt.io.model.request.BinStats;
import com.bdavt.io.model.request.CompStats;
import com.bdavt.io.model.request.Correlation;
import com.bdavt.io.model.request.DescriptiveAnalysis;
import com.bdavt.io.model.request.NullCount;
import com.bdavt.io.model.request.PredictiveAnalysis;
import com.bdavt.io.model.request.Schema;
import com.bdavt.io.mongo.model.History;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

@Service
public class HistoryDao {
	
	@Autowired
	private DB mongo;
	
	public DBObject saveDocument(DBObject doc) {
		DBCollection collection = mongo.getCollection("history");
		collection.save(doc);
		return doc;
	}
	
	public WriteResult updateDocument(BasicDBObject query, BasicDBObject updateDoc) {
		DBCollection collection = mongo.getCollection("history");
		return collection.update(query, updateDoc);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject createDocument(String username) throws ParseException {
		DBCollection collection = mongo.getCollection("history");
		History history = new History();
		history.init();
		history.setUsername(username);
		DBObject doc = history.getDbObject();
		collection.save(doc);
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
		schemaEntry.put("data", schema);
		
		DBCollection collection = mongo.getCollection("history");
		
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(executionId));
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("schema", schemaEntry.toJSONString());
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$push", newDocument);

		collection.update(query, updateObject);
	}
	
	@SuppressWarnings("unchecked")
	public void saveDescriptiveStats(DescriptiveAnalysis payload, JSONArray schema) {
		String executionId = payload.getExecutionId();
		JSONObject schemaEntry = new JSONObject();
		schemaEntry.put("dataset", payload.getDataset());
		schemaEntry.put("profile", payload.getProfile());
		schemaEntry.put("username", payload.getUsername());
		schemaEntry.put("columns", payload.getColumns());
		schemaEntry.put("data", schema);
		
		DBCollection collection = mongo.getCollection("history");
		
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(executionId));
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("descriptiveStats", schemaEntry.toJSONString());
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$push", newDocument);

		collection.update(query, updateObject);
	}
	
	@SuppressWarnings("unchecked")
	public void saveCorrelation(Correlation payload, JSONObject correlation) {
		String executionId = payload.getExecutionId();
		JSONObject schemaEntry = new JSONObject();
		schemaEntry.put("dataset", payload.getDataset());
		schemaEntry.put("profile", payload.getProfile());
		schemaEntry.put("username", payload.getUsername());
		schemaEntry.put("columns", payload.getColumns());
		schemaEntry.put("data", correlation);
		
		DBCollection collection = mongo.getCollection("history");
		
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(executionId));
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("correlation", schemaEntry.toJSONString());
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$push", newDocument);

		collection.update(query, updateObject);
	}
	
	@SuppressWarnings("unchecked")
	public void savePredictiveStats(PredictiveAnalysis payload, JSONArray stats) {
		String executionId = payload.getExecutionId();
		JSONObject schemaEntry = new JSONObject();
		schemaEntry.put("dataset", payload.getDataset());
		schemaEntry.put("profile", payload.getProfile());
		schemaEntry.put("username", payload.getUsername());
		schemaEntry.put("columns", payload.getColumns());
		schemaEntry.put("trainingPercent", payload.getThresholdPercantage());
		schemaEntry.put("validationPercent", payload.getValidationPercentage());
		
		schemaEntry.put("data", stats);
		
		DBCollection collection = mongo.getCollection("history");
		
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(executionId));
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("predictiveStats", schemaEntry.toJSONString());
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$push", newDocument);

		collection.update(query, updateObject);
	}
	
	@SuppressWarnings("unchecked")
	public void saveBinStats(BinStats payload, JSONObject stats) {
		String executionId = payload.getExecutionId();
		JSONObject schemaEntry = new JSONObject();
		schemaEntry.put("dataset", payload.getDataset());
		schemaEntry.put("profile", payload.getProfile());
		schemaEntry.put("username", payload.getUsername());
		schemaEntry.put("columns", payload.getColumns());
		schemaEntry.put("data", stats);
		
		DBCollection collection = mongo.getCollection("history");
		
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(executionId));
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("binStats", schemaEntry.toJSONString());
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$push", newDocument);

		collection.update(query, updateObject);
	}
	
	@SuppressWarnings("unchecked")
	public void saveNullCount(NullCount payload, JSONObject stats) {
		String executionId = payload.getExecutionId();
		JSONObject schemaEntry = new JSONObject();
		schemaEntry.put("dataset", payload.getDataset());
		schemaEntry.put("profile", payload.getProfile());
		schemaEntry.put("username", payload.getUsername());
		schemaEntry.put("column", payload.getColumn());
		schemaEntry.put("data", stats);
		
		DBCollection collection = mongo.getCollection("history");
		
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(executionId));
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("nullCount", schemaEntry.toJSONString());
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$push", newDocument);

		collection.update(query, updateObject);
	}
	
	@SuppressWarnings("unchecked")
	public void saveCompStats(CompStats payload, JSONArray stats) {
		String executionId = payload.getExecutionId();
		JSONObject schemaEntry = new JSONObject();
		schemaEntry.put("dataset1", payload.getDataset1());
		schemaEntry.put("dataset2", payload.getDataset2());
		schemaEntry.put("username", payload.getUsername());
		schemaEntry.put("column", payload.getColumn());
		schemaEntry.put("data", stats);
		
		DBCollection collection = mongo.getCollection("history");
		
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(executionId));
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("compStats", schemaEntry.toJSONString());
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$push", newDocument);

		collection.update(query, updateObject);
	}
}
