package com.dvenci.mongo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AnalysisHistory {
	
	@Id
	private String id;

	private String username;
	private Date timestamp;
	
	@DBRef
	private List<SchemaHistory> schema;
	
	@DBRef
	private List<DescStatsHistory> descriptiveStats;
	
	@DBRef
	private List<CorrelationHistory> correlation;
	
	@DBRef
	private List<PredictiveStatsHistory> predictiveStats;
	
	@DBRef
	private List<FeatureEngHistory> featureEngineering;
	
	@DBRef
	private List<TargetPredictHistory> targetPrediction;
	
	@DBRef
	private List<BinStatsHistory> binStats;
	
	@DBRef
	private List<NullCountHistory> nullCount;
	
	@DBRef
	private List<CompStatHistory> compStats;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<SchemaHistory> getSchema() {
		return schema;
	}

	public void setSchema(List<SchemaHistory> schema) {
		this.schema = schema;
	}

	public List<DescStatsHistory> getDescriptiveStats() {
		return descriptiveStats;
	}

	public void setDescriptiveStats(List<DescStatsHistory> descriptiveStats) {
		this.descriptiveStats = descriptiveStats;
	}

	public List<CorrelationHistory> getCorrelation() {
		return correlation;
	}

	public void setCorrelation(List<CorrelationHistory> correlation) {
		this.correlation = correlation;
	}

	public List<PredictiveStatsHistory> getPredictiveStats() {
		return predictiveStats;
	}

	public void setPredictiveStats(List<PredictiveStatsHistory> predictiveStats) {
		this.predictiveStats = predictiveStats;
	}

	public List<FeatureEngHistory> getFeatureEngineering() {
		return featureEngineering;
	}

	public void setFeatureEngineering(List<FeatureEngHistory> featureEngineering) {
		this.featureEngineering = featureEngineering;
	}

	public List<BinStatsHistory> getBinStats() {
		return binStats;
	}

	public void setBinStats(List<BinStatsHistory> binStats) {
		this.binStats = binStats;
	}

	public List<NullCountHistory> getNullCount() {
		return nullCount;
	}

	public void setNullCount(List<NullCountHistory> nullCount) {
		this.nullCount = nullCount;
	}

	public List<CompStatHistory> getCompStats() {
		return compStats;
	}

	public void setCompStats(List<CompStatHistory> compStats) {
		this.compStats = compStats;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TargetPredictHistory> getTargetPrediction() {
		return targetPrediction;
	}

	public void setTargetPrediction(List<TargetPredictHistory> targetPrediction) {
		this.targetPrediction = targetPrediction;
	}
}
