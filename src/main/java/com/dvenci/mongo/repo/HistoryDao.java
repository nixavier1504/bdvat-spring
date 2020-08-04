package com.dvenci.mongo.repo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvenci.http.model.request.BinStats;
import com.dvenci.http.model.request.CompStats;
import com.dvenci.http.model.request.Correlation;
import com.dvenci.http.model.request.DescriptiveAnalysis;
import com.dvenci.http.model.request.NullCount;
import com.dvenci.http.model.request.PredictiveAnalysis;
import com.dvenci.http.model.request.Schema;
import com.dvenci.mongo.model.History;
import com.dvenci.utils.HistoryUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
	public JSONArray getTimeStamps(String username) {
		DBCollection collection = mongo.getCollection("history");
		JSONArray result = new JSONArray();
		
		BasicDBObject query = new BasicDBObject();
		query.put("username", username);
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("timestamp", true);
		fields.put("_id", true);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		
		DBCursor cursor = collection.find(query, fields);
		while (cursor.hasNext()) {
			DBObject dbObject = (DBObject) cursor.next();
			try {
				JSONObject entry = (JSONObject) new JSONParser().parse(JSON.serialize(dbObject)); 
				entry.put("_id", dbObject.get("_id").toString());
				Date timestamp = (Date)dbObject.get("timestamp");
				entry.put("timestamp", df.format(timestamp));
				result.add(entry);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getHistoryById(String _id) {
		DBCollection collection = mongo.getCollection("history");
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(_id));
		DBCursor cursor = collection.find(query);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		while(cursor.hasNext()) {
			DBObject dbObject = (DBObject) cursor.next();
			try {
				JSONObject entry = (JSONObject) new JSONParser().parse(JSON.serialize(dbObject));
				entry.put("_id", dbObject.get("_id").toString());
				entry.put("timestamp", df.format(dbObject.get("timestamp")));
				entry.put("schema", HistoryUtils.strToJson((JSONArray) entry.get("schema")));
				entry.put("descriptiveStats", HistoryUtils.strToJson((JSONArray) entry.get("descriptiveStats")));
				entry.put("correlation", HistoryUtils.strToJson((JSONArray) entry.get("correlation")));
				entry.put("compStats", HistoryUtils.strToJson((JSONArray) entry.get("compStats")));
				entry.put("featureEngineering", HistoryUtils.strToJson((JSONArray) entry.get("featureEngineering")));
				entry.put("binStats", HistoryUtils.strToJson((JSONArray) entry.get("binStats")));
				entry.put("nullCount", HistoryUtils.strToJson((JSONArray) entry.get("nullCount")));
				entry.put("predictiveStats", HistoryUtils.strToJson((JSONArray) entry.get("predictiveStats")));
				return entry;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
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
		schemaEntry.put("trainingPercent", payload.getThresholdPercentage());
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
