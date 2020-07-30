/**
 * Author: Amit Kumar
 * amitkumar647
 * 12:01:45 am
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.service;

import org.springframework.stereotype.Service;

import com.bdavt.io.constants.AppConstants;
import com.bdavt.io.model.datacomparison;
import com.bdavt.io.model.statistics;
import com.bdavt.io.model.statistics2;
import com.bdavt.io.utils.GetSessionIdUser;
import com.bdavt.io.utils.GetSessionPyIDUser;
import com.bdavt.io.utils.callSparkSession;
import com.bdavt.io.utils.callSparkSession2;
import com.bdavt.io.utils.mongoDBCrud;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author amitkumar647
 *
 */

@Service
public class livySchemaServiceImpl{

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");

	public JSONArray process(String path,String mode) throws Exception {
		//String path=envModel.getPathOfFile();
		String exec_id=currentTimestamp();
		callSparkSession ss = new callSparkSession();
		JSONObject result=ss.callSpark(path,mode);
		Object data=result.get(AppConstants.RESULT);
		JSONParser parser = new JSONParser();
		JSONObject json_object = (JSONObject) parser.parse(removeSpecialChar((String)data));
		return getJsonObject(json_object,exec_id);
	}
	public JSONArray process(String user,String path, String profile, String mode) throws Exception {

		long uid=encodeHash(user,mode);
		String exec_id=currentTimestamp();
		GetSessionIdUser get_sess_id= new GetSessionIdUser();
		Integer session_id_livy=get_sess_id.return_session_id(user);

		callSparkSession ss = new callSparkSession();
		JSONObject result=ss.callSpark(path,mode,session_id_livy);
		Object data=result.get(AppConstants.RESULT);
		JSONParser parser = new JSONParser();
		JSONObject json_object = (JSONObject) parser.parse(removeSpecialChar((String)data));
		JSONArray res =getJsonObject(json_object,exec_id);
		if(!res.isEmpty()) {
			mongoDBCrud.dbInsert(uid,exec_id,user, path, res.toString(), currentTime(), mode,profile);
		}
		return res;
	}
	
	//public JSONArray process1(String user,String path, String profile, String colname,String mode) throws Exception {
		
		public JSONArray process1(String user,String path, String profile, String colname,String mode) throws Exception {
	

		//try {
		//	System.out.println("colname is"+colname);
          //  Thread.sleep(100000);
        //} catch (InterruptedException ie)
        //{
          //  System.out.println("Scanning...");
        //}

		long uid=encodeHash(user,mode);
		String exec_id=currentTimestamp();
		GetSessionIdUser get_sess_id= new GetSessionIdUser();
		Integer session_id_livy=get_sess_id.return_session_id(user);
		
		
		

		callSparkSession ss = new callSparkSession();
		JSONObject result=ss.callSparkfornull(path,mode,session_id_livy,colname);
		//String result_dis="";
		//JSONObject result_dis=ss.callSparkfornull(path,mode,session_id_livy,colname);

		JSONObject result_dis=ss.callSparkfordistinct(path,mode,session_id_livy,colname);
		System.out.println("response to layer is "+result);
		System.out.println("response to layersssssss is "+result_dis);
		Object data=result.get(AppConstants.RESPONSE_APP_JSON);
		Object data1=result_dis.get(AppConstants.RESPONSE_APP_JSON);
		System.out.println("response to layer is data "+data);
		
		Map<String, String> number1 = new HashMap<>();
		number1.put("nullcount", data.toString());
		
		
		
		System.out.println("response to layersssss is data "+number1);
		Gson gson = new Gson();
		String numbersJson = gson.toJson(number1);

		
		//Map<String, String> path1 = new HashMap<>();
		//path1.put("path", data.toString());

		System.out.println("response to layersssssssssssss is data "+numbersJson);

		
		JSONParser parser = new JSONParser();
		JSONObject json_object = (JSONObject) parser.parse(numbersJson.toString());

		System.out.println("response to layer is datas "+json_object);
		json_object.put("DistinctCount",data1.toString());
		json_object.put("DatasetPath",path);
		//return json_object;
		JSONArray res =getJsonObject(json_object,exec_id);
	//JSONArray res = new JSONArray();
		res.add(json_object);
	if(!res.isEmpty()) {
		mongoDBCrud.dbInsert(uid,exec_id,user, path, res.toString(), currentTime(), mode,profile);
	}
	System.out.println("response to layer is datass "+res);
		return res;
	}
	

	@SuppressWarnings("unchecked")
	public JSONObject process2(statistics model,String mode) throws Exception {

		String user=model.getUsername();
		String path=model.getDataset();
		String cols=model.getColumns();
		String exec_id=model.getExec_id();
		String profile=model.getProfile();

		JSONObject res=null;
		long uid=encodeHash(user,mode);
		System.out.println("inside getstats");
		System.out.println("user is :"+ user);
		//String path=envModel.getPathOfFile();
		GetSessionPyIDUser get_sess_id= new GetSessionPyIDUser();
		Integer session_id_livy=get_sess_id.return_session_id(user);

		System.out.println("Livy session id gathered is "+session_id_livy);
		callSparkSession2 ss = new callSparkSession2();


		if(mode.equalsIgnoreCase("descriptive")) {
			res=ss.callPySpark(path,cols,mode,session_id_livy,0,0);
		}else if(mode.equalsIgnoreCase("correlation")){
			res=ss.callPySpark(path,cols,mode,session_id_livy,0,0);
		}
		//storing selected schema only
		if(!res.isEmpty()) {
			mongoDBCrud.dbInsert(uid,exec_id,user, path, res.toString(), currentTime(), mode,profile);
		}
		return res;
	}

	
	@SuppressWarnings("unchecked")
	public JSONObject process4(statistics model,String mode) throws Exception {

		String user=model.getUsername();
		String path=model.getDataset();
		String cols=model.getColumns();
		String exec_id=model.getExec_id();
		String profile=model.getProfile();

		JSONObject res=null;
		long uid=encodeHash(user,mode);
		System.out.println("inside getstats");
		System.out.println("user is :"+ user);
		//String path=envModel.getPathOfFile();
		GetSessionPyIDUser get_sess_id= new GetSessionPyIDUser();
		Integer session_id_livy=get_sess_id.return_session_id(user);

		System.out.println("Livy session id gathered is "+session_id_livy);
		callSparkSession2 ss = new callSparkSession2();


		//if(mode.equalsIgnoreCase("descriptive")) {
			res=ss.callPySpark(path,cols,mode,session_id_livy,0,0);
		//}else if(mode.equalsIgnoreCase("correlation")){
		//	//res=ss.callPySpark(path,cols,mode,session_id_livy,0,0);
		//}
		//storing selected schema only
		if(!res.isEmpty()) {
			mongoDBCrud.dbInsert(uid,exec_id,user, path, res.toString(), currentTime(), mode,profile);
		}
		return res;
	}
	
	
	@SuppressWarnings("unchecked")
	
	public JSONObject process5(String user,String path1, String path2, String colname,String mode) throws Exception {

	long uid=encodeHash(user,mode);
	String exec_id=currentTimestamp();
	GetSessionIdUser get_sess_id= new GetSessionIdUser();
	Integer session_id_livy=get_sess_id.return_session_id(user);
	
	
	

	callSparkSession ss = new callSparkSession();
	JSONObject result=ss.callSparkforcomp(path1,path2,mode,session_id_livy,colname);


	//JSONObject result_dis=ss.callSparkfordistinct(path1,mode,session_id_livy,colname);
	System.out.println("response to layer is "+result);
	//System.out.println("response to layersssssss is "+result_dis);
	Object data=result.get(AppConstants.RESPONSE_APP_JSON);
	//Object data1=result_dis.get(AppConstants.RESPONSE_APP_JSON);
	System.out.println("response to layer is data "+data);
	
	Map<String, String> number1 = new HashMap<>();
	number1.put("mismatch Count [[Dataset1 vs Dataset 2],[Dataset 2 vs Dataset 1]]", data.toString());
	
	
	
	System.out.println("response to layersssss is data "+number1);
	Gson gson = new Gson();
	String numbersJson = gson.toJson(number1);



	System.out.println("response to layersssssssssssss is data "+numbersJson);

	
	JSONParser parser = new JSONParser();
	JSONObject json_object = (JSONObject) parser.parse(numbersJson.toString());

	System.out.println("response to layer is datas "+json_object);
	
	
	//json_object.put("DistinctCount",data1.toString());
	//json_object.put("DatasetPath",path1);

	//JSONArray res =getJsonObject(json_object,exec_id);

	//res.add(json_object);
//if(!res.isEmpty()) {
//	mongoDBCrud.dbInsert(uid,exec_id,user, path1, res.toString(), currentTime(), mode,profile);
//}
//System.out.println("response to layer is datass "+res);
	return json_object;
}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONArray process3(statistics model,String mode) throws Exception {

		String user=model.getUsername();
		String path=model.getDataset();
		String cols=model.getColumns();
		String exec_id=model.getExec_id();
		String profile=model.getProfile();

		JSONArray res=new JSONArray();
		long uid=encodeHash(user,mode);
		System.out.println("inside getstats");
		System.out.println("user is :"+ user);
		//String path=envModel.getPathOfFile();
		GetSessionPyIDUser get_sess_id= new GetSessionPyIDUser();
		Integer session_id_livy=get_sess_id.return_session_id(user);

		System.out.println("Livy session id gathered is "+session_id_livy);
		callSparkSession2 ss = new callSparkSession2();


		if(mode.equalsIgnoreCase("predictive")){
			int tp=model.getThresholdPercantage();
			int vp=model.getValidationPercentage();
			JSONObject res1=ss.callPySpark(path,cols,mode,session_id_livy,tp,vp);
			JSONObject temp = new JSONObject();
			temp.put(AppConstants.GETVALIDATION, vp);
			JSONObject temp1 = new JSONObject();
			temp1.put(AppConstants.GETTRAINING, tp);
			res.add(res1);
			res.add(temp);
			res.add(temp1);


		}else if(mode.equalsIgnoreCase("predictive_sk")){
			int tp=model.getThresholdPercantage();
			int vp=model.getValidationPercentage();
			JSONObject res1=ss.callPySpark(path,cols,mode,session_id_livy,tp,vp);
			JSONObject temp = new JSONObject();
			temp.put(AppConstants.GETVALIDATION, vp);
			JSONObject temp1 = new JSONObject();
			temp1.put(AppConstants.GETTRAINING, tp);
			res.add(res1);
			res.add(temp);
			res.add(temp1);
		}
		//storing selected schema only
		if(!res.isEmpty()) {
			mongoDBCrud.dbInsert(uid,exec_id,user, path, res.toString(), currentTime(), mode,profile);
		}
		return res;
	}

	
	
	
	
	
	@SuppressWarnings("unchecked")
	public JSONArray process33(statistics2 model,String mode) throws Exception {

		String user=model.getUsername();
		String path=model.getDataset();
		String cols=model.getColumns();
		String cols1=model.getColumns1();
		String exec_id=model.getExec_id();
		String profile=model.getProfile();

		JSONArray res=new JSONArray();
		long uid=encodeHash(user,mode);
		System.out.println("inside getstats");
		System.out.println("user is :"+ user);
		//String path=envModel.getPathOfFile();
		GetSessionPyIDUser get_sess_id= new GetSessionPyIDUser();
		Integer session_id_livy=get_sess_id.return_session_id(user);

		System.out.println("Livy session id gathered is "+session_id_livy);
		callSparkSession2 ss = new callSparkSession2();


	
			int tp=model.getThresholdPercantage();
			int vp=model.getValidationPercentage();
			JSONObject res1=ss.callPySpark2(path,cols,cols1,mode,session_id_livy,tp,vp);
			JSONObject temp = new JSONObject();
			temp.put(AppConstants.GETVALIDATION, vp);
			JSONObject temp1 = new JSONObject();
			temp1.put(AppConstants.GETTRAINING, tp);
			res.add(res1);
			res.add(temp);
			res.add(temp1);


		
		//storing selected schema only
		if(!res.isEmpty()) {
			mongoDBCrud.dbInsert(uid,exec_id,user, path, res.toString(), currentTime(), mode,profile);
		}
		return res;
	}
	
	
	@SuppressWarnings("unchecked")
	public JSONArray predicttarget(statistics2 model,String mode) throws Exception {

		String user=model.getUsername();
		String path=model.getDataset();
		String cols=model.getColumns();
		String cols1=model.getColumns1();
		String exec_id=model.getExec_id();
		String profile=model.getProfile();

		JSONArray res=new JSONArray();
		long uid=encodeHash(user,mode);
		System.out.println("inside getstats");
		System.out.println("user is :"+ user);
		//String path=envModel.getPathOfFile();
		GetSessionPyIDUser get_sess_id= new GetSessionPyIDUser();
		Integer session_id_livy=get_sess_id.return_session_id(user);

		System.out.println("Livy session id gathered is "+session_id_livy);
		callSparkSession2 ss = new callSparkSession2();


	
			int tp=model.getThresholdPercantage();
			int vp=model.getValidationPercentage();
			JSONObject res1=ss.callPySparkforprediction(path,cols,cols1,mode,session_id_livy,tp,vp);
			JSONObject temp = new JSONObject();
			temp.put(AppConstants.GETVALIDATION, vp);
			JSONObject temp1 = new JSONObject();
			temp1.put(AppConstants.GETTRAINING, tp);
			res.add(res1);
			res.add(temp);
			res.add(temp1);


		
		//storing selected schema only
		if(!res.isEmpty()) {
			mongoDBCrud.dbInsert(uid,exec_id,user, path, res.toString(), currentTime(), mode,profile);
		}
		return res;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public static JSONArray getJsonObject(JSONObject jsonObj,String exec_id) {
		JSONArray json_arr=new JSONArray();
		int id=1;
		for (Object key : jsonObj.keySet()) {
			//based on you key types
			JSONObject resp_obj = new JSONObject();
			String keyStr = (String)key;
			Object keyvalue = jsonObj.get(keyStr);
			//Print key and value
			resp_obj.put(AppConstants.ID, id);
			if(keyvalue.equals(AppConstants.STRINGTYPE)) {
				resp_obj.put("isSelected", AppConstants.FALSE);
			}else {
				resp_obj.put("isSelected", AppConstants.TRUE);
			}
			resp_obj.put(AppConstants.FIELDNAME, keyStr);
			resp_obj.put(AppConstants.DATATYPE, keyvalue);
			json_arr.add(resp_obj);
			System.out.println("key: "+ keyStr + " value: " + keyvalue);
			id++;
		}
		JSONObject resp_obj_key = new JSONObject();
		resp_obj_key.put("exec_id", exec_id);
		json_arr.add(resp_obj_key);
		return json_arr;
	}
	
	
	
	
	
	
	
	public static String removeSpecialChar(String str) {
		return str.replaceAll("\\\\", "");
	}

	public static String currentTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp);
		return sdf.format(timestamp);
	}
	public static String currentTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp);
		return sdf1.format(timestamp);
	}

	public static long encodeHash(String username,String profile) {

		long millis = Instant.now().toEpochMilli();
		StringBuffer str=new StringBuffer();
		str.append(username);
		str.append("^");
		str.append(profile);
		str.append("^");
		str.append(millis);
		return str.hashCode();
	}

}

