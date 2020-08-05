package com.dvenci.http.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompStats {

	private String username;
	private String dataset1;
	private String dataset2;
    private String column;
    private String executionId;

}
