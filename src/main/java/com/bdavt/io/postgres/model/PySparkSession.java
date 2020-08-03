package com.bdavt.io.postgres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PySparkSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false)
	private Long sessionId;
	
	public PySparkSession() {
		
	}

	public PySparkSession(String username, Long sessionId) {
		this.username = username;
		this.sessionId = sessionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

}
