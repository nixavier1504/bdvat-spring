/**
 * Author: Amit Kumar
 * amitkumar647
 * 4:48:03 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.utils;


import java.net.UnknownHostException;

import org.json.simple.JSONArray;

import com.bdavt.io.constants.AppConstants;
import com.bdavt.io.model.mongoPojo;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
/**
 * @author amitkumar647
 *
 */
public class mongoDBCrud {
	
	//static MongoClientURI mongoConnectionURI = new MongoClientURI("mongodb://amitkumar647:Ilovemyrtr@1332@localhost:27017/local");

	public static void dbInsert(long uid,String eid,String username, String dataset,String Data, String timestamp, String mode, String profile) throws UnknownHostException
	{
		
		MongoClient mongo = new MongoClient(AppConstants.MONGO_HOSTNAME, 27017);
		//MongoClient mongo = new MongoClient(mongoConnectionURI);
		DB db = mongo.getDB(AppConstants.MONGO_DB);
		DBCollection col = db.getCollection(AppConstants.MONGO_COLLECTION);
		mongoPojo mp = createMap(uid,eid,username,dataset,Data,timestamp,mode,profile);
		DBObject doc = createDBObject(mp);
		WriteResult result = col.insert(doc);
		mongo.close();

	}
	
	public static JSONArray dbGet(String username) throws UnknownHostException
	{
		MongoClient mongo = new MongoClient(AppConstants.MONGO_HOSTNAME, 27017);
		//MongoClient mongo = new MongoClient(mongoConnectionURI);
		System.out.println(username);
		JSONArray res_json=new JSONArray();
		DB db = mongo.getDB(AppConstants.MONGO_DB);
		boolean isExists=isCollectionExists(db,AppConstants.MONGO_COLLECTION);
		if(!isExists) {
			db.createCollection(AppConstants.MONGO_COLLECTION, null);
		}
		DBCollection col = db.getCollection(AppConstants.MONGO_COLLECTION);
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("username", username);
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("uid", 1);
		fields.put("username", 2);
		fields.put("timestamp", 3);
		fields.put("dataset", 4);
		fields.put("mode", 5);
		fields.put("profile", 6);
		fields.put("executionid", 7);
		fields.put("_id", 0);
		DBCursor cursor = col.find(whereQuery,fields);
		DBObject temp=null;
		while(cursor.hasNext()){
			temp=cursor.next();
			System.out.println(temp);
			res_json.add(temp);
		}
		mongo.close();
		return res_json;
	}

	public static mongoPojo createMap(long uid,String executionid,String username, String dataset,String Data, String timestamp, String mode, String profile) {
		mongoPojo mp = new mongoPojo();
		mp.setUid(uid);
		mp.setEid(executionid);
		mp.setUsername(username);
		mp.setMode(mode);
		mp.setTimestamp(timestamp);
		mp.setData(Data);
		mp.setDataset(dataset);
		mp.setProfile(profile);
		return mp;
	}

	public static DBObject createDBObject(mongoPojo mp) {
		BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

		docBuilder.append("uid", mp.getUid());
		docBuilder.append("executionid", mp.getEid());
		docBuilder.append("username", mp.getUsername());
		docBuilder.append("data", mp.getData());
		docBuilder.append("timestamp", mp.getTimestamp());
		docBuilder.append("dataset", mp.getDataset());
		docBuilder.append("mode", mp.getMode());
		docBuilder.append("profile", mp.getProfile());
		return docBuilder.get();
	}
	
	public static boolean isCollectionExists(DB db, String collectionName) 
	{
	    DBCollection table = db.getCollection(collectionName);
	    return (table.count()>0)?true:false;
	}

	/**
	 * @param uid
	 * @return
	 * @throws UnknownHostException 
	 */
	public static JSONArray dbGetData(long uid) throws UnknownHostException {
		// TODO Auto-generated method stub
		MongoClient mongo = new MongoClient(AppConstants.MONGO_HOSTNAME, 27017);
		//MongoClient mongo = new MongoClient(mongoConnectionURI);
		System.out.println(uid);
		JSONArray res_json=new JSONArray();
		DB db = mongo.getDB(AppConstants.MONGO_DB);
		boolean isExists=isCollectionExists(db,AppConstants.MONGO_COLLECTION);
		if(!isExists) {
			db.createCollection(AppConstants.MONGO_COLLECTION, null);
		}
		DBCollection col = db.getCollection(AppConstants.MONGO_COLLECTION);
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("uid", uid);
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("uid", 1);
		fields.put("username", 2);
		fields.put("timestamp", 3);
		fields.put("dataset", 4);
		fields.put("mode", 5);
		fields.put("data", 6);
		fields.put("executionid", 7);
		fields.put("profile", 8);
		fields.put("_id", 0);
		DBCursor cursor = col.find(whereQuery,fields);
		DBObject temp=null;
		while(cursor.hasNext()){
			temp=cursor.next();
			System.out.println(temp);
			res_json.add(temp);
		}
		mongo.close();
		return res_json;
	}
	
	public static JSONArray dbGetData(String username,String exec_id) throws UnknownHostException {
		// TODO Auto-generated method stub
		MongoClient mongo = new MongoClient(AppConstants.MONGO_HOSTNAME, 27017);
		//MongoClient mongo = new MongoClient(mongoConnectionURI);
		System.out.println(exec_id);
		System.out.println(username);
		JSONArray res_json=new JSONArray();
		DB db = mongo.getDB(AppConstants.MONGO_DB);
		boolean isExists=isCollectionExists(db,AppConstants.MONGO_COLLECTION);
		if(!isExists) {
			db.createCollection(AppConstants.MONGO_COLLECTION, null);
		}
		DBCollection col = db.getCollection(AppConstants.MONGO_COLLECTION);
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("username", username);
		whereQuery.put("executionid", exec_id);
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("uid", 1);
		fields.put("username", 2);
		fields.put("timestamp", 3);
		fields.put("dataset", 4);
		fields.put("mode", 5);
		fields.put("data", 6);
		fields.put("executionid", 7);
		fields.put("profile", 8);
		fields.put("_id", 0);
		DBCursor cursor = col.find(whereQuery,fields);
		DBObject temp=null;
		while(cursor.hasNext()){
			temp=cursor.next();
			System.out.println(temp);
			res_json.add(temp);
		}
		mongo.close();
		return res_json;
	}

}
