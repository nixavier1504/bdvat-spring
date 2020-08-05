package com.dvenci.mongo.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FeatureEngHistory {

	@Id
	private String id;
	private String executionId;
	private String username;
	private String profile;
	private String columns;
	private String targetColumn;
	private String dataset;
	private int thresholdPercentage;
	private int validationPercentage;
	private Object data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public int getThresholdPercentage() {
		return thresholdPercentage;
	}
	public void setThresholdPercentage(int thresholdPercentage) {
		this.thresholdPercentage = thresholdPercentage;
	}
	public int getValidationPercentage() {
		return validationPercentage;
	}
	public void setValidationPercentage(int validationPercentage) {
		this.validationPercentage = validationPercentage;
	}
	public String getTargetColumn() {
		return targetColumn;
	}
	public void setTargetColumn(String targetColumn) {
		this.targetColumn = targetColumn;
	}
	
}
