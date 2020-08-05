package com.dvenci.mongo.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CompStatHistory {
	
	@Id
	private String id;
	private String executionId;
	private String username;
	private String column;
	private String dataset1;
	private String dataset2;
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
	public String getDataset1() {
		return dataset1;
	}
	public void setDataset1(String dataset1) {
		this.dataset1 = dataset1;
	}
	public String getDataset2() {
		return dataset2;
	}
	public void setDataset2(String dataset2) {
		this.dataset2 = dataset2;
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
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
}
