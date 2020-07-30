/**
 * Author: Amit Kumar
 * amitkumar647
 * 7:55:11 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.utils;

import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import com.bdavt.io.constants.AppConstants;

/**
 * @author amitkumar647
 *
 */
public class callSparkSession {

	@SuppressWarnings("unchecked")
	public JSONObject callSpark(String path, String mode) throws Exception {

		JSONObject resp_obj = new JSONObject();
		try {
			System.out.println("INFO:LIVY LIVY");
			callLivySessionId ses_Id = new callLivySessionId(); 
			String sessionid=ses_Id.getSessionID().toString();
			System.out.println(sessionid);

			livyFileStatementId stId=new livyFileStatementId();
			String statementid=null;

			if(mode.equalsIgnoreCase("file")) {
				statementid=stId.listFileExecute(sessionid,path).toString();
			}else if(mode.equalsIgnoreCase("schema")){
				statementid=stId.getSchema(sessionid, path).toString();
			}
			System.out.println("statement id is "+statementid);

			livyGetResponse get_resp = new livyGetResponse();

			//TimeUnit.SECONDS.sleep(60);

			JSONObject sd=get_resp.getResponse(sessionid,statementid);
			Object status = sd.get(AppConstants.RESPONSE_STATE);


			while(!status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)) {

				TimeUnit.MICROSECONDS.sleep(200);
				sd=get_resp.getResponse(sessionid,statementid);
				status=sd.get(AppConstants.RESPONSE_STATE);
				if(status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)){
					break;
				}
				System.out.println(status);
			}

			JSONObject output=(JSONObject) sd.get(AppConstants.RESPONSE_OUTPUT);
			JSONObject data=(JSONObject) output.get(AppConstants.RESPONSE_DATA);
			System.out.println(sd.toString());
			System.out.println("generated response");
			resp_obj.put(AppConstants.RESULT, data.get(AppConstants.RESPONSE_APP_JSON));
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp_obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject callSpark(String path, String mode,Integer session_sid) throws Exception {

		JSONObject resp_obj = new JSONObject();
		try {
			System.out.println("INFO:LIVY LIVY");
			String sessionid=session_sid.toString();
			//TimeUnit.SECONDS.sleep(60);
			livyFileStatementId stId=new livyFileStatementId();
			String statementid=null;

			if(mode.equalsIgnoreCase("file")) {
				statementid=stId.listFileExecute(sessionid,path).toString();
			}else if(mode.equalsIgnoreCase("schema")){
				System.out.println("inside schema");
				statementid=stId.getSchema(sessionid, path).toString();
				}
				
			System.out.println("statement id is "+statementid);

			livyGetResponse get_resp = new livyGetResponse();

			//TimeUnit.SECONDS.sleep(60);

			JSONObject sd=get_resp.getResponse(sessionid,statementid);
			Object status = sd.get(AppConstants.RESPONSE_STATE);


			while(!status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)) {

				TimeUnit.MICROSECONDS.sleep(200);
				sd=get_resp.getResponse(sessionid,statementid);
				status=sd.get(AppConstants.RESPONSE_STATE);
				if(status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)){
					break;
				}
				System.out.println(status);
			}

			JSONObject output=(JSONObject) sd.get(AppConstants.RESPONSE_OUTPUT);
			JSONObject data=(JSONObject) output.get(AppConstants.RESPONSE_DATA);
			System.out.println(sd.toString());
			System.out.println("generated responses");

			resp_obj.put(AppConstants.RESULT, data.get(AppConstants.RESPONSE_APP_JSON));
		}catch(Exception e){
			e.printStackTrace();
		}
		return resp_obj;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONObject callSparkfornull(String path, String mode,Integer session_sid,String colname) throws Exception {

		JSONObject resp_obj = new JSONObject();
		try {
			System.out.println("INFO:LIVY LIVY");
			String sessionid=session_sid.toString();
			//TimeUnit.SECONDS.sleep(60);
			livyFileStatementId stId=new livyFileStatementId();
			String statementid=null;

				System.out.println("inside null");
				statementid=stId.getnullcount(sessionid, path,colname).toString();
		
			System.out.println("statement id is "+statementid);

			livyGetResponse get_resp = new livyGetResponse();

			//TimeUnit.SECONDS.sleep(60);

			JSONObject sd=get_resp.getResponse(sessionid,statementid);
			Object status = sd.get(AppConstants.RESPONSE_STATE);


			while(!status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)) {

				TimeUnit.MICROSECONDS.sleep(200);
				sd=get_resp.getResponse(sessionid,statementid);
				status=sd.get(AppConstants.RESPONSE_STATE);
				if(status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)){
					break;
				}
				System.out.println(status);
			}
			
			//String sd1 = new JSONObject().put("nnu",sd).toString();
			

			JSONObject output=(JSONObject) sd.get(AppConstants.RESPONSE_OUTPUT);
			JSONObject data=(JSONObject) output.get(AppConstants.RESPONSE_DATA);
			System.out.println(data.toString());
			System.out.println("output is "+sd.toString());
			System.out.println("generated responsess");
			resp_obj=data;
			System.out.println("generated responsesssssssss"+resp_obj);
			//resp_obj.put(AppConstants.RESULT, data.get(AppConstants.RESPONSE_APP_JSON));
		}catch(Exception e){
		e.printStackTrace();
		}
		System.out.println("generated responsesssssssss"+resp_obj);
		return resp_obj;

	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONObject callSparkforcomp(String path1, String path2,String mode,Integer session_sid,String colname) throws Exception {

		JSONObject resp_obj = new JSONObject();
		try {
			System.out.println("INFO:LIVY LIVY");
			String sessionid=session_sid.toString();
			//TimeUnit.SECONDS.sleep(60);
			livyFileStatementId stId=new livyFileStatementId();
			String statementid=null;

				System.out.println("inside null");
				statementid=stId.getcompcount(sessionid, path1,path2,colname).toString();
		
			System.out.println("statement id is "+statementid);

			livyGetResponse get_resp = new livyGetResponse();

			//TimeUnit.SECONDS.sleep(60);

			JSONObject sd=get_resp.getResponse(sessionid,statementid);
			Object status = sd.get(AppConstants.RESPONSE_STATE);


			while(!status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)) {

				TimeUnit.MICROSECONDS.sleep(200);
				sd=get_resp.getResponse(sessionid,statementid);
				status=sd.get(AppConstants.RESPONSE_STATE);
				if(status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)){
					break;
				}
				System.out.println(status);
			}
			
			//String sd1 = new JSONObject().put("nnu",sd).toString();
			

			JSONObject output=(JSONObject) sd.get(AppConstants.RESPONSE_OUTPUT);
			JSONObject data=(JSONObject) output.get(AppConstants.RESPONSE_DATA);
			System.out.println(data.toString());
			System.out.println("output is "+sd.toString());
			System.out.println("generated responsess");
			resp_obj=data;
			System.out.println("generated responsesssssssss"+resp_obj);
			//resp_obj.put(AppConstants.RESULT, data.get(AppConstants.RESPONSE_APP_JSON));
		}catch(Exception e){
		e.printStackTrace();
		}
		System.out.println("generated responsesssssssss"+resp_obj);
		return resp_obj;

	}
	
	@SuppressWarnings("unchecked")
	public JSONObject callSparkfordistinct(String path, String mode,Integer session_sid,String colname) throws Exception {

		JSONObject resp_obj = new JSONObject();
		try {
			System.out.println("INFO:LIVY LIVY");
			String sessionid=session_sid.toString();
			//TimeUnit.SECONDS.sleep(60);
			livyFileStatementId stId=new livyFileStatementId();
			String statementid=null;

				System.out.println("inside null");
				statementid=stId.getdistinctcount(sessionid, path,colname).toString();
		
			System.out.println("statement id is "+statementid);

			livyGetResponse get_resp = new livyGetResponse();

			//TimeUnit.SECONDS.sleep(60);

			JSONObject sd=get_resp.getResponse(sessionid,statementid);
			Object status = sd.get(AppConstants.RESPONSE_STATE);


			while(!status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)) {

				TimeUnit.MICROSECONDS.sleep(200);
				sd=get_resp.getResponse(sessionid,statementid);
				status=sd.get(AppConstants.RESPONSE_STATE);
				if(status.equals(AppConstants.RESPONSE_STATE_AVAILABLE)){
					break;
				}
				System.out.println(status);
			}
			
			//String sd1 = new JSONObject().put("nnu",sd).toString();
			

			JSONObject output=(JSONObject) sd.get(AppConstants.RESPONSE_OUTPUT);
			JSONObject data=(JSONObject) output.get(AppConstants.RESPONSE_DATA);
			System.out.println(data.toString());
			System.out.println("output is "+sd.toString());
			System.out.println("generated responsess");
			resp_obj=data;
			System.out.println("generated responsesssssssss"+resp_obj);
			//resp_obj.put(AppConstants.RESULT, data.get(AppConstants.RESPONSE_APP_JSON));
		}catch(Exception e){
		e.printStackTrace();
		}
		System.out.println("generated responsesssssssss"+resp_obj);
		return resp_obj;

	}
	
	
	
	public static String removeSpecialChar(String str) {
		return str.replaceAll("\\\\", "");
	}

}
