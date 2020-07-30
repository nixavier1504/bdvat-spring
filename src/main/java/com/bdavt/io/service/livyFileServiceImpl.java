/**
 * Author: Amit Kumar
 * amitkumar647
 * 11:26:47 am
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.bdavt.io.utils.GetSessionIdUser;
import com.bdavt.io.utils.GetSessionPyIDUser;
import com.bdavt.io.utils.callSparkSession;
import com.bdavt.io.utils.callSparkSession2;


/**
 * @author amitkumar647
 *
 */
@Service
public class livyFileServiceImpl{

	public JSONObject process(String path,String mode) throws Exception {
		//String path=envModel.getPathOfFile();
		callSparkSession ss = new callSparkSession();
		return ss.callSpark(path,mode);
	}
	public JSONObject process(String user,String path, String mode) throws Exception {

		GetSessionIdUser get_sess_id= new GetSessionIdUser();
		Integer session_id_livy=get_sess_id.return_session_id(user);

		callSparkSession ss = new callSparkSession();
		return ss.callSpark(path,mode,session_id_livy);
	}
	
}

