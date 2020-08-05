package com.dvenci.mongo.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class CorrelationHistory {
	
	@Id
	private String id;
	private String executionId;
	private String username;
	private String profile;
	private String columns;
	private String dataset;
	private Object data;
	
}
