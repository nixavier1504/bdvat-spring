package com.dvenci.mongo.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
}
