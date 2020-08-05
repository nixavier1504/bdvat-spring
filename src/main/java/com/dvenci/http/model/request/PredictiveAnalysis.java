package com.dvenci.http.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictiveAnalysis {
	
	private String username;
	private String dataset;
    private String columns;
    private String profile;
    private String executionId;
	private int thresholdPercentage;
	private int validationPercentage;
	
}
