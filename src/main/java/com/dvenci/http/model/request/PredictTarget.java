package com.dvenci.http.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictTarget {
	
	private String username;
	private String dataset;
	private String columns;
	private String targetColumn;
	private String profile;
	private int thresholdPercentage;
	private int validationPercentage;
	private String executionId;
	
}
