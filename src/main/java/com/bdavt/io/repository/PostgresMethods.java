package com.bdavt.io.repository;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public interface PostgresMethods {
	public Long getSessionId(String type, String username) throws ClassNotFoundException, SQLException;
	public void updateSessionId(String type, String username, Long sessionId) throws ClassNotFoundException, SQLException;
	public void insertSessionId(String type, String username, Long sessionId) throws ClassNotFoundException, SQLException;
}
