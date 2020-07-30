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
public class callSparkSession2 {

	@SuppressWarnings("unchecked")
	public JSONObject callPySpark(String path,String cols, String mode,Integer session_sid, int tp,int vp) throws Exception {

		JSONObject resp_obj = new JSONObject();
		try {
			System.out.println("INFO:LIVY stats LIVY");
			String sessionid=session_sid.toString();
			//TimeUnit.SECONDS.sleep(60);
			livyFileStatementId stId=new livyFileStatementId();
			String statementid=null;

			if(mode.equalsIgnoreCase("descriptive")) {
				statementid=stId.getStats(sessionid,path,cols).toString();
			}else if(mode.equalsIgnoreCase("correlation")){
				statementid=stId.getCorrStats(sessionid, path,cols).toString();
			}else if(mode.equalsIgnoreCase("predictive")){
				statementid=stId.getLinearReg(sessionid,path,cols,tp,vp).toString();
			}else if(mode.equalsIgnoreCase("predictive_sk")){
				statementid=stId.getSKLearnReg(sessionid,path,cols,tp,vp).toString();
			}else if(mode.equalsIgnoreCase("binstats")){
					statementid=stId.getBinStats(sessionid,path,cols).toString();
			
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
	public JSONObject callPySpark2(String path,String cols,String cols1, String mode,Integer session_sid, int tp,int vp) throws Exception {

		JSONObject resp_obj = new JSONObject();
		try {
			System.out.println("INFO:LIVY stats LIVY");
			String sessionid=session_sid.toString();
			//TimeUnit.SECONDS.sleep(60);
			livyFileStatementId stId=new livyFileStatementId();
			String statementid=null;

			statementid=stId.getLinearReg1(sessionid,path,cols,cols1,tp,vp).toString();			

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
	public JSONObject callPySparkforprediction(String path,String cols,String cols1, String mode,Integer session_sid, int tp,int vp) throws Exception {

		JSONObject resp_obj = new JSONObject();
		try {
			System.out.println("INFO:LIVY stats LIVY");
			String sessionid=session_sid.toString();
			//TimeUnit.SECONDS.sleep(60);
			livyFileStatementId stId=new livyFileStatementId();
			String statementid=null;

			statementid=stId.getPrediction(sessionid,path,cols,cols1,tp,vp).toString();			

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

	public static String removeSpecialChar(String str) {
		return str.replaceAll("\\\\", "");
	}

}
