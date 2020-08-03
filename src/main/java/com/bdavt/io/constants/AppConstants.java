/**
 * Author: Amit Kumar
 * amitkumar647
 * 8:21:59 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.constants;

/**
 * @author amitkumar647
 *
 */
public class AppConstants {
	
	public static String ID="id";
	public static String LIVY_PORT="8998";
	public static String LIVY_HOSTNAME="13.127.233.209";
	public static long LIVY_POST_HOLD=10;
	
	public static String RESPONSE_OUTPUT="output";
	public static String RESPONSE_DATA="data";
	public static String RESPONSE_APP_JSON="application/json";
	public static String RESULT="result";
	public static String SESSION_ID="sessionId";
	public static String STATEMENT_ID="statementId";
	public static String RESPONSE_STATUS="status";
	public static String RESPONSE_STATUS_OK="ok";
	public static String RESPONSE_STATE="state";
	public static String RESPONSE_STATE_AVAILABLE="available";
	public static String RESPONSE_STATE_WAIT="waiting";
	public static String RESPONSE_STATE_DEAD="dead";
	
	public static String TRUE="true";
	public static String FALSE="false";
	public static String STRINGTYPE="StringType";
	public static String FIELDNAME="fieldName";
	public static String DATATYPE="dataType";
	
	public static String LISTFILE="file";
	public static String GETSCHEMA="schema";
	public static String NULLCOUNT="nullcount";
	public static String GETDESC="descriptive";
	public static String GETBINSTATS="binstats";
	
	public static String GETPRED="predictive";
	public static String GETPREDSK="predictive_sk";
	public static String GETCORR="correlation";
	public static String GETPREDFE="predictiveFE";
	
	public static String GETVALIDATION="validation_percent";
	public static String GETTRAINING="training_percent";
	
	//********POSTGRES SNET**************
	
//	public static String POSTGRES_USERNAME="bavtuser";
//	public static String POSTGRES_PASSWORD="bavtuser";
//	public static String POSTGRES_DB="bavtdb";
//	public static String POSTGRES_HOSTNAME="10.118.62.209";
//	public static String POSTGRES_PORT="5432";
//	
	//********POSTGRES LOCAL*************
	
	public static String POSTGRES_USERNAME="postgres";
	public static String POSTGRES_PASSWORD="password";
	public static String POSTGRES_DB="bdvat";
	public static String POSTGRES_HOSTNAME="localhost";
	public static String POSTGRES_PORT="5432";
	
	
	//********MONGODB SNET***************
	public static String MONGO_KEYCOLUMN="username";
	public static String MONGO_PORT="27017";
	public static String MONGO_HOSTNAME="10.118.62.209";
	public static String MONGO_DB="local";
	public static String MONGO_COLLECTION="users_history";
	public static String MONGO_USERNAME="bavtuser";
	public static String MONGO_PASSWORD="bavtuser";
	
	//********MONGODB LOCAL**************
	/*
	public static String MONGO_KEYCOLUMN="username";
	public static String MONGO_PORT="27017";
	public static String MONGO_HOSTNAME="localhost";
	public static String MONGO_DB="local";
	public static String MONGO_COLLECTION="users_history";
	public static String MONGO_USERNAME="";
	public static String MONGO_PASSWORD="";
	*/

}
