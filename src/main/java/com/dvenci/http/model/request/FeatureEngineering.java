package com.dvenci.http.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeatureEngineering {
	
	private String username;
	private String dataset;
	private String columns;
	private String targetColumn;
	private String profile;
	private int thresholdPercentage;
	private int validationPercentage;
	private String executionId;
	
}
