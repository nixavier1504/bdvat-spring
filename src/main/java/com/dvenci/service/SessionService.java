package com.dvenci.service;

public interface SessionService {
	
	public Long getSparkSession(String username) throws Exception;
	public Long getPySparkSession(String username) throws Exception;

}
