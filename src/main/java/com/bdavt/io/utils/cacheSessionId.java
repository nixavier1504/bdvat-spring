package com.bdavt.io.utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;

import com.bdavt.io.constants.AppConstants;

public class cacheSessionId {
	
	//# Time in milliseconds on how long Livy will wait before timing out an idle session.
	//livy.server.session.timeout = 1h
	@SuppressWarnings("unused")
	//@Scheduled(fixedRate = 3600000)
	public void create_pool_sessions () throws ParseException, UnsupportedEncodingException, InterruptedException, SQLException, ClassNotFoundException {
		Long newsessionid = null;
		String id="";
		Integer flag=0;
		Connection c = null;
		Statement stmt = null;
		//Statement stmt1 = null;
		//Statement stmt1 = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://"+AppConstants.POSTGRES_HOSTNAME+":"+AppConstants.POSTGRES_PORT+"/"+AppConstants.POSTGRES_DB,AppConstants.POSTGRES_USERNAME, AppConstants.POSTGRES_PASSWORD);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( " SELECT distinct username  FROM public.config_json_x" );
			while ( rs.next() ) {
				flag=1;
				id = rs.getString("username");
				System.out.println( "username = " + id );
				//
				System.out.println("creating new session for username" + id);
				HttpClient httpclient1 = HttpClients.createDefault();
				HttpPost httppost1 = new HttpPost("http://localhost:8998/sessions/");
				String content1=null;
				String json = "{\n\t\"kind\": \"spark\"\n}";
				StringEntity entity1 = new StringEntity(json);
				httppost1.setEntity(entity1);
				httppost1.setHeader("Accept", "application/json");
				httppost1.setHeader("Content-type", "application/json");
				httppost1.setHeader("X-Requested-By", "sharjain");
				try {
					System.out.println("hitting");
					HttpResponse response1 = httpclient1.execute(httppost1);
					HttpEntity respEntity1 = response1.getEntity();
					if (respEntity1 != null) {
						content1 =  EntityUtils.toString(respEntity1);
						System.out.println("content is "+content1);
					}
				} catch (ClientProtocolException e) {
					// writing exception to log
					e.printStackTrace();
				} catch (IOException e) {
					// writing exception to log
					e.printStackTrace();
				}
				JSONParser parser1 = new JSONParser();
				JSONObject json2 = (JSONObject) parser1.parse(content1);
				Long newsessid = (Long) json2.get("id");
				String status_of_session =  (String) json2.get("state");
				System.out.println("new session id : " + newsessid);
				newsessionid=newsessid;
				while(!status_of_session.equals("idle")) {
					//TimeUnit.SECONDS.sleep(5);
					System.out.println("loop");
					HttpGet getre = new HttpGet("http://localhost:8998/sessions/"+newsessid);
					getre.setHeader("Accept", "application/json");
					getre.setHeader("Content-type", "application/json");
					getre.setHeader("X-Requested-By", "sharjain");
					try {
						HttpResponse response = httpclient1.execute(getre);
						HttpEntity respEntity = response.getEntity();
						if (respEntity != null) {
							content1 =  EntityUtils.toString(respEntity);
							System.out.println(content1);
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					JSONParser parser2 = new JSONParser();
					JSONObject json_object2 = (JSONObject) parser1.parse(content1);
					System.out.println("printing"+json_object2.toString());
					String session_Status1 = (String) json_object2.get("state");
					System.out.println("Session_state is : " + session_Status1);
					status_of_session=session_Status1;
				}   
				//stmt1 = c.createStatement();

				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection("jdbc:postgresql://"+AppConstants.POSTGRES_HOSTNAME+":"+AppConstants.POSTGRES_PORT+"/"+AppConstants.POSTGRES_DB,AppConstants.POSTGRES_USERNAME, AppConstants.POSTGRES_PASSWORD);
				c.setAutoCommit(true);
				System.out.println("Opened database successfully");
				stmt = c.createStatement();

				PreparedStatement stmt1 = c.prepareStatement("INSERT into public.user_pro(username, session_id) values(?,?)");
				stmt1.setString(1, id);
				stmt1.setLong(2, newsessionid);
				stmt1.execute();

				stmt.close();
				c.close();
			} 
			rs.close();

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			//System.exit(0);
		}
	}}

