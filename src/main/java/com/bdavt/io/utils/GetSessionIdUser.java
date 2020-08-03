package com.bdavt.io.utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
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

import com.bdavt.io.constants.AppConstants;
public class GetSessionIdUser {
	@SuppressWarnings("unused")
	public Integer return_session_id ( String username ) throws ParseException, UnsupportedEncodingException, InterruptedException, SQLException, ClassNotFoundException {
		Long newsessionid = null;
		Integer id=0;
		Integer flag=0;
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://"+AppConstants.POSTGRES_HOSTNAME+":"+AppConstants.POSTGRES_PORT+"/"+AppConstants.POSTGRES_DB,AppConstants.POSTGRES_USERNAME, AppConstants.POSTGRES_PASSWORD);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( " SELECT * FROM public.user_pro where username='"+username+"'" );
			while ( rs.next() ) {
				flag=1;
				id = rs.getInt("session_id");
				System.out.println( "ssssssssID = " + id );
			}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			System.exit(0);
		}
		System.out.println("idddddd is "+ id);
		System.out.println("Operation done successfully");
		String content=null;
		HttpClient httpclient = HttpClients.createDefault();
		HttpGet httppost = new HttpGet("http://13.127.233.209:8998/sessions/"+id);
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("X-Requested-By", "sharjain");
		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity respEntity = response.getEntity();
			if (respEntity != null) {
				content =  EntityUtils.toString(respEntity);
				System.out.println(content);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String session_Status=null;
		JSONParser parser = new JSONParser();
		try {
			
			JSONObject json_object = (JSONObject) parser.parse(content);
			System.out.println("printing ssss"+json_object.toString());
			session_Status = (String) json_object.get("state");
			System.out.println("Session_state is : " + session_Status);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//start  a new session if session id is not active
		if (session_Status != null && session_Status.equalsIgnoreCase("idle")) {
			System.out.println("prv session is active");
			newsessionid=id.longValue();
		} else {
			System.out.println("creating new session");
			HttpClient httpclient1 = HttpClients.createDefault();
			HttpPost httppost1 = new HttpPost("http://13.127.233.209:8998/sessions/");
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
			while(  !status_of_session.equals("idle")  ) {
				TimeUnit.SECONDS.sleep(5);
				System.out.println("loop");
				HttpGet getre = new HttpGet("http://13.127.233.209:8998/sessions/"+newsessid);
				getre.setHeader("Accept", "application/json");
				getre.setHeader("Content-type", "application/json");
				getre.setHeader("X-Requested-By", "sharjain");
				try {
					HttpResponse response = httpclient.execute(getre);
					HttpEntity respEntity = response.getEntity();
					if (respEntity != null) {
						content =  EntityUtils.toString(respEntity);
						System.out.println(content);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				JSONParser parser2 = new JSONParser();
				JSONObject json_object2 = (JSONObject) parser.parse(content);
				System.out.println("printing"+json_object2.toString());
				String session_Status1 = (String) json_object2.get("state");
				System.out.println("Session_state is : " + session_Status1);
				status_of_session=session_Status1;
			}   
			System.out.println("out of loop.");
		}
		System.out.println(newsessionid);
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection("jdbc:postgresql://"+AppConstants.POSTGRES_HOSTNAME+":"+AppConstants.POSTGRES_PORT+"/"+AppConstants.POSTGRES_DB,AppConstants.POSTGRES_USERNAME, AppConstants.POSTGRES_PASSWORD);
		c.setAutoCommit(false);
		System.out.println("Opened database successfully");
		stmt = c.createStatement();
		if(flag==0) {
			PreparedStatement stmt1 = c.prepareStatement("INSERT into public.user_pro(username, session_id) values(?,?)");
			stmt1.setString(1, username);
			stmt1.setLong(2, newsessionid);
			stmt1.execute();

		}else {
			PreparedStatement stmt1 = c.prepareStatement("UPDATE public.user_pro set session_id = ? where username=?");
			stmt1.setLong(1, newsessionid);
			stmt1.setString(2, username);
			stmt1.executeUpdate();
		}

		//ResultSet rs = stmt1.executeQuery();
		//stmt.executeUpdate(sql);
		c.commit();
		return newsessionid.intValue();
	}   
}




