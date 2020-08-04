package com.dvenci.http.model.request;

public class PredictTarget {
	
	private String username;
	private String dataset;
	private String columns;
	private String targetColumn;
	private String profile;
	private int thresholdPercentage;
	private int validationPercentage;
	private String executionId;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getTargetColumn() {
		return targetColumn;
	}
	public void setTargetColumn(String targetColumn) {
		this.targetColumn = targetColumn;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
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
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	
}
