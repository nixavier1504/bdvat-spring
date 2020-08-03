package com.bdavt.io.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bdavt.io.constants.AppConstants;
import com.bdavt.io.repository.PostgresMethods;

@Service
public class PostgresService implements PostgresMethods{
	
	@Value("${postgres.host}")
	private String POSTGRES_HOSTNAME;
	
	@Value("${postgres.port}")
	private String POSTGRES_PORT;
	
	@Value("${postgres.db}")
	private String POSTGRES_DB;
	
	@Value("${postgres.username}")
	private String POSTGRES_USERNAME;
	
	@Value("${postgres.password}")
	private String POSTGRES_PASSWORD;
	
	private HashMap<String, String> sessionTables = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("spark", "user_pro");
			put("pyspark", "user_py_pro");
		}
	};
	
	public PostgresService() {
		// TODO Auto-generated constructor stub
	}
	
	private Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		Connection c = DriverManager.getConnection("jdbc:postgresql://"+ POSTGRES_HOSTNAME +":"+ POSTGRES_PORT +"/"+POSTGRES_DB, POSTGRES_USERNAME, POSTGRES_PASSWORD);
		return c;
	}

	@Override
	public Long getSessionId(String type, String username) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery( "SELECT * FROM public." + sessionTables.get(type) + " where username='"+username+"'" );
		Long id = null;
		while (rs.next()) {
			id = rs.getLong("session_id");
		}
		rs.close();
		stmt.close();
		c.close();
		return id;
	}

	@Override
	public void updateSessionId(String type, String username, Long sessionId) throws ClassNotFoundException, SQLException {
		System.out.println("Updating session Id in db");
		Connection c = getConnection();
		PreparedStatement stmt1 = c.prepareStatement("UPDATE public." + sessionTables.get(type) + " set session_id = ? where username=?");
		stmt1.setLong(1, sessionId);
		stmt1.setString(2, username);
		stmt1.executeUpdate();
		c.close();
	}

	@Override
	public void insertSessionId(String type, String username, Long sessionId) throws ClassNotFoundException, SQLException {
		System.out.println("Inserting session id");
		Connection c = getConnection();
		PreparedStatement stmt1 = c.prepareStatement("INSERT into public." + sessionTables.get(type) + "(username, session_id) values(?,?)");
		stmt1.setString(1, username);
		stmt1.setLong(2, sessionId);
		stmt1.execute();
		c.close();
	}
	
	

}
