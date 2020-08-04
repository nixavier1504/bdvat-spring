package com.dvenci.model.request;

public class PredictiveAnalysis {
	
	private String username;
	private String dataset;
    private String columns;
    private String profile;
    private String executionId;
	private int thresholdPercentage;
	private int validationPercentage;
	
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
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String exec_id) {
		executionId = exec_id;
	}
	public int getThresholdPercentage() {
		return thresholdPercentage;
	}
	public void setThresholdPercentage(int thresholdPercantage) {
		this.thresholdPercentage = thresholdPercantage;
	}
	public int getValidationPercentage() {
		return validationPercentage;
	}
	public void setValidationPercentage(int validationPercentage) {
		this.validationPercentage = validationPercentage;
	}
}
