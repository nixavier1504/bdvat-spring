package com.dvenci.http.model.request;

public class NullCount {
	
	private String username;
    private String dataset;
    private String profile;
	private String column;
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
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String columns) {
		this.column = columns;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
}