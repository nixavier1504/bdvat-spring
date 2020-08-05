package com.dvenci.http.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DescriptiveAnalysis {

	private String username;
	private String dataset;
    private String columns;
    private String profile;
    private String executionId;
    
}
