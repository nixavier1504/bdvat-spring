package com.bdavt.io.mongo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class History {
	private String _id;
	private String username;
	private Date timestamp;
	private List<Object> schema;
	private List<Object> descriptiveStats;
	private List<Object> correlation;
	private List<Object> predictiveStats;
	private List<Object> featureEngineering;
	private List<Object> binStats;
	private List<Object> nullCount;
	private List<Object> compStats;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Object> getSchema() {
		return schema;
	}

	public void setSchema(List<Object> schema) {
		this.schema = schema;
	}

	public List<Object> getDescriptiveStats() {
		return descriptiveStats;
	}

	public void setDescriptiveStats(List<Object> descriptiveStats) {
		this.descriptiveStats = descriptiveStats;
	}

	public List<Object> getCorrelation() {
		return correlation;
	}

	public void setCorrelation(List<Object> correlation) {
		this.correlation = correlation;
	}

	public List<Object> getPredictiveStats() {
		return predictiveStats;
	}

	public void setPredictiveStats(List<Object> predictiveStats) {
		this.predictiveStats = predictiveStats;
	}

	public List<Object> getFeatureEngineering() {
		return featureEngineering;
	}

	public void setFeatureEngineering(List<Object> featureEngineering) {
		this.featureEngineering = featureEngineering;
	}

	public List<Object> getBinStats() {
		return binStats;
	}

	public void setBinStats(List<Object> binStats) {
		this.binStats = binStats;
	}

	public List<Object> getNullCount() {
		return nullCount;
	}

	public void setNullCount(List<Object> nullCount) {
		this.nullCount = nullCount;
	}

	public List<Object> getCompStats() {
		return compStats;
	}

	public void setCompStats(List<Object> compStats) {
		this.compStats = compStats;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void init() {
		this._id = null;
		this.username = "";
		this.timestamp = new Date();
		this.schema = new ArrayList<Object>();
		this.descriptiveStats = new ArrayList<Object>();
		this.correlation = new ArrayList<Object>();
		this.predictiveStats = new ArrayList<Object>();
		this.featureEngineering = new ArrayList<Object>();
		this.binStats = new ArrayList<Object>();
		this.nullCount = new ArrayList<Object>();
		this.compStats = new ArrayList<Object>();
	}
	
	public DBObject getDbObject() {
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
		builder.add("_id", _id);
		builder.add("username", username);
		builder.add("schema", schema);
		builder.add("timestamp", timestamp);
		builder.add("descriptiveStats", descriptiveStats);
		builder.add("correlation", correlation);
		builder.add("predictiveStats", predictiveStats);
		builder.add("featureEngineering", featureEngineering);
		builder.add("binStats", binStats);
		builder.add("nullCount", nullCount);
		builder.add("compStats", compStats);
		return builder.get();
	}
}
