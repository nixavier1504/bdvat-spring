/**
 * Author: Sharad Jain
 * Sharad_jain
 * 2:52:31 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bdavt.io.constants.AppConstants;

/**
 * @author Sharad_jain
 *
 */
public class livyFileStatementId {
	
	public static String py_file="hdfs:/user/sharjain/python/bavt_stats.py";
	
	public String listFileExecute (String sessionid,String path) throws Exception {
		System.out.println(sessionid);
		//TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
		String content=null;
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
		
		// Interactive Scala code to fetch list of files.
		String json="{\"code\":\"import org.apache.hadoop.fs.FileSystem;"
				+ "import org.apache.hadoop.fs.Path;"
				+ "def getListOfFiles(dir: String):String = {"
				+ "val conf = sc.hadoopConfiguration;"
				+ "val fs = FileSystem.get(conf);"
				+ "val files = fs.listStatus(new Path(dir));"
				+ "val a = files.map(_.getPath().toString);"
				+ "var r : StringBuilder = new StringBuilder();"
			//	+ "a.foreach {"
			//	+ "x => r.append(x.trim);"
			//	+ "r.append(\",\");"
			//	+ "}"
				+ "val final_str=r.toString().dropRight(1);"
				+ "return final_str };"
				+ "val files_x = getListOfFiles(\\\""+path+"\\\") \\n%json files_x\"}";
		
		
		StringEntity entity = new StringEntity(json);
		httppost.setEntity(entity);
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("X-Requested-By", "sharjain");
		System.out.println("statementid is running  " + sessionid);

		try {
			System.out.println("http info is  " + httppost.toString());
			HttpResponse response = httpclient.execute(httppost);
			System.out.println("http response  is  " + response.toString());
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
		// return content;
		System.out.println("content is "+content);
		JSONParser parser = new JSONParser();
		JSONObject json_object = (JSONObject) parser.parse(content);
		Long statementId = (Long) json_object.get(AppConstants.ID);
		System.out.println("statementid: " + statementId);
		return statementId.toString();
	}
	
	public String getSchema (String sessionid,String path) throws Exception {
		System.out.println(sessionid);
		//TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
		String content=null;
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
		String dir=path+"*";
		// Interactive Scala code to fetch list of files.
		String json="{\"code\":\" import org.apache.spark.sql.SQLContext;"
				+ "import scala.util.parsing.json.JSONObject;"
				+ "def schemaParquet(path: String): String={"
				+ "val sqlContext = new SQLContext(sc);"
				+ "val schemaFile = sqlContext.read.parquet(path.toString).dtypes.toMap;"
				+ "return JSONObject(schemaFile).toString() };"
				+ "val schema = schemaParquet(\\\""+dir+"\\\") \\n%json schema\"}";
		
		
		StringEntity entity = new StringEntity(json);
		httppost.setEntity(entity);
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("X-Requested-By", "sharjain");
		System.out.println("statementid is running  " + sessionid);

		try {
			System.out.println("http info is  " + httppost.toString());
			HttpResponse response = httpclient.execute(httppost);
			System.out.println("http response  is  " + response.toString());
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
		// return content;
		System.out.println("content is "+content);
		JSONParser parser = new JSONParser();
		JSONObject json_object = (JSONObject) parser.parse(content);
		Long statementId = (Long) json_object.get(AppConstants.ID);
		System.out.println("statementid: " + statementId);
		return statementId.toString();
	}
	
	
	public String getnullcount (String sessionid,String path,String colname) throws Exception {
		System.out.println(sessionid);
		//TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
		String content=null;
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
		String dir=path+"*";
		//		String col="_c0";
		String col=colname;
		// Interactive Scala code to fetch list of files.
	       String json="{\"code\":\" import org.apache.spark.sql.SQLContext;"
	                + "import scala.util.parsing.json.JSONObject;"
	                + "def schemaParquet(path: String,col: String): String={"
	                + "val sqlContext = new SQLContext(sc);"
	                + "val schemaFile_temp = sqlContext.read.parquet(path.toString);"
	                + "val schemaFile = schemaFile_temp.filter(schemaFile_temp(col).isNull || schemaFile_temp(col) === \\\"\\\").count;"
	                + "return schemaFile.toString() };"
	        		+ "val schema = schemaParquet(\\\""+dir+"\\\"" +","+"\\\""+col+"\\\""+") \\n%json schema\"}";
	    
	       
		System.out.println("json is  " + json);
		
		StringEntity entity = new StringEntity(json);
		httppost.setEntity(entity);
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("X-Requested-By", "sharjain");
		System.out.println("statementid is running  " + sessionid);

		try {
			System.out.println("http info is  " + httppost.toString());
			HttpResponse response = httpclient.execute(httppost);
			System.out.println("http response  is  " + response.toString());
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
		// return content;
		System.out.println("content is "+content);
		JSONParser parser = new JSONParser();
		JSONObject json_object = (JSONObject) parser.parse(content);
		Long statementId = (Long) json_object.get(AppConstants.ID);
		System.out.println("statementid: " + statementId);
		return statementId.toString();
	}
	
	
	public String getcompcount (String sessionid,String path1,String path2 , String colname) throws Exception {
		System.out.println(sessionid);
		//TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
		String content=null;
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
		String dir1=path1+"*";
		String dir2=path2+"*";
		//		String col="_c0";
		String col=colname;
		// Interactive Scala code to fetch list of files.
		//String json="{\"code\":\" import org.apache.spark.sql.SQLContext;"
			//	+"import scala.util.parsing.json.JSONObject;"
			//	+"val path1 = \\\""+dir1+"\\\";"
			//	+"val path2 = \\\""+dir2+"\\\";"
			//	+"val dataset1 = spark.read.parquet(path1);"
			//	+"dataset1.registerTempTable(\\\"dataset11\\\");"
			//	+"val dataset2 = spark.read.parquet(path2);"
			//	+"dataset2.registerTempTable(\\\"dataset22\\\");"
			//	+"val diff=spark.sql(\\\" select  '1-2' as category , (select count(*) from (select _c0 from dataset11 minus select  _c0 from dataset22) ) as count union  select  '2-1' as category , (select count(*) from (select _c0 from dataset22 minus select  _c0 from dataset11) ) as count \\\")"
			//	+ "\\n%json diff.toString()_\"}";
				//+ " \"}";

		  String json="{\"code\":\" import org.apache.spark.sql.SQLContext;"
		        + "import scala.util.parsing.json.JSONObject;"
		        + "def schemaParquet(path1: String,path2: String,col: String): String={"
		        + "val sqlContext = new SQLContext(sc);"
		        + "val dataset1 = sqlContext.read.parquet(path1);"
		        + "val dataset2 = sqlContext.read.parquet(path2);"
		        + "dataset1.registerTempTable(\\\"dataset11\\\");"
		        + "dataset2.registerTempTable(\\\"dataset22\\\");"
		        +  "val d1= dataset1.select(\\\"city_mpg\\\");"
		        +  "val d2= dataset2.select(\\\"city_mpg\\\");"
		        +  "val d3=d1.except(d2).count();"
		        +  "val d4=d2.except(d1).count();"
		        + "return d3.toString+"+"\\\",\\\""+"+"+"d4.toString };"
		        //+ "return d3.toString"+"+"+ "d4.toString };"
		        //+ "val schema = schemaParquet(\\\""+dir1+"\\\"" +",\\\""+dir2+"\\\"" +","+"\\\""+col+"\\\""+") \\n%json schema\"}";  
		    	+ "val schema = schemaParquet(\\\""+dir1+"\\\"" +","+"\\\""+dir2+"\\\""+","+"\\\""+col+"\\\""+") \\n%json schema\"}";		
		
		  /*String json="{\"code\":\" import org.apache.spark.sql.SQLContext;"
			        + "import scala.util.parsing.json.JSONObject;"
			        + "def schemaParquet(path1: String,path2: String,col: String): String={"
			        + "val sqlContext = new SQLContext(sc);"
			        + "val dataset1 = sqlContext.read.parquet(path1);"
			        + "val dataset2 = sqlContext.read.parquet(path2);"
			        + "dataset1.registerTempTable(\\\"dataset11\\\");"
			        + "dataset2.registerTempTable(\\\"dataset22\\\");"
			        + "val schemaFile=sqlContext.sql(\\\" select  '1-2' as category , (select count(1) from (select "+col+" from dataset11 minus select "+col+" from dataset22) ) as count union  select  '2-1' as category , (select count(1) from (select "+col+" from dataset22 minus select "+col+" from dataset11) ) as count \\\");"
			        //+ "return schemaFile.toString() };"
			        + "return schemaFile.select(\\\"count\\\").collect().mkString(\\\"[\\\", \\\",\\\", \\\"]\\\") };"
			        //+ "val schema = schemaParquet(\\\""+dir1+"\\\"" +",\\\""+dir2+"\\\"" +","+"\\\""+col+"\\\""+") \\n%json schema\"}";  
			    	+ "val schema = schemaParquet(\\\""+dir1+"\\\"" +","+"\\\""+dir2+"\\\""+","+"\\\""+col+"\\\""+") \\n%json schema\"}";
		  
		*/
		  /*
		  String json="{\"code\":\" import org.apache.spark.sql.SQLContext;"
			        + "import scala.util.parsing.json.JSONObject;"
			        + "def schemaParquet(path1: String,path2: String): String={"
			        + "val sqlContext = new SQLContext(sc);"
			        + "val dataset1 = spark.read.parquet(path1);"
			        + "val dataset2 = spark.read.parquet(path2);"
			        + "dataset1.registerTempTable(\\\"dataset11\\\");"
			        + "dataset2.registerTempTable(\\\"dataset22\\\");"
			        + "val schemaFile=spark.sql(\\\" select  '1-2' as category , (select count(*) from (select _c0 from dataset11 minus select  _c0 from dataset22) ) as count union  select  '2-1' as category , (select count(*) from (select _c0 from dataset22 minus select  _c0 from dataset11) ) as count \\\");"
			        //+ "return schemaFile.toString() };"
			        + "return schemaFile.select(\\\"count\\\").collect().mkString(\\\"[\\\", \\\",\\\", \\\"]\\\") };"
			        //+ "val schema = schemaParquet(\\\""+dir1+"\\\"" +",\\\""+dir2+"\\\"" +","+"\\\""+col+"\\\""+") \\n%json schema\"}";  
					+ "val schema = schemaParquet(\\\""+dir1+"\\\"" +","+"\\\""+dir2+"\\\""+") \\n%json schema\"}";


	               */

	                
	    		   
	               // + "def schemaParquet(path: String,col: String): String={"
	               // + "val sqlContext = new SQLContext(sc);"
	               // + "val schemaFile_temp = sqlContext.read.parquet(path.toString);"
	               // + "val schemaFile = schemaFile_temp.filter(schemaFile_temp(col).isNull || schemaFile_temp(col) === \\\"\\\").count;"
	                //+ "return schemaFile.toString() };"
	    
	       
		System.out.println("json is  " + json);
		
		StringEntity entity = new StringEntity(json);
		httppost.setEntity(entity);
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("X-Requested-By", "sharjain");
		System.out.println("statementid is running  " + sessionid);

		try {
			System.out.println("http info is  " + httppost.toString());
			HttpResponse response = httpclient.execute(httppost);
			System.out.println("http response  is  " + response.toString());
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
		// return content;
		System.out.println("content is "+content);
		JSONParser parser = new JSONParser();
		JSONObject json_object = (JSONObject) parser.parse(content);
		Long statementId = (Long) json_object.get(AppConstants.ID);
		System.out.println("statementid: " + statementId);
		return statementId.toString();
	}
	
	
	public String getdistinctcount (String sessionid,String path,String colname) throws Exception {
		System.out.println(sessionid);
		//TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
		String content=null;
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
		String dir=path+"*";
		String col=colname;
		// Interactive Scala code to fetch list of files.
		String json="{\"code\":\" import org.apache.spark.sql.SQLContext;"
				+ "import scala.util.parsing.json.JSONObject;"
				+ "def schemaParquet(path: String,col: String): String={"
				+ "val sqlContext = new SQLContext(sc);"
				+ "val schemaFile_temp = sqlContext.read.parquet(path.toString);"
				+ "val schemaFile = schemaFile_temp.select(col).distinct.count;"
				+ "return schemaFile.toString() };"
				+ "val schema = schemaParquet(\\\""+dir+"\\\"" +","+"\\\""+col+"\\\""+") \\n%json schema\"}";		

		
		StringEntity entity = new StringEntity(json);
		httppost.setEntity(entity);
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("X-Requested-By", "sharjain");
		System.out.println("statementid is running  " + sessionid);

		try {
			System.out.println("http info is  " + httppost.toString());
			HttpResponse response = httpclient.execute(httppost);
			System.out.println("http response  is  " + response.toString());
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
		// return content;
		System.out.println("content is "+content);
		JSONParser parser = new JSONParser();
		JSONObject json_object = (JSONObject) parser.parse(content);
		Long statementId = (Long) json_object.get(AppConstants.ID);
		System.out.println("statementid: " + statementId);
		return statementId.toString();
	}
	
	public String getStats (String sessionid,String path,String cols) throws Exception {
        System.out.println(sessionid);
        //TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
        String content=null;
        String dir=path+"*";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
        String json = "{\"code\": \"sc.addFile(\\\""+py_file+"\\\");\\n"
        		+ "import bavt_stats as bt;\\n"
        		+ "x = bt.desc_stats(\\\""+dir+"\\\","+cols+",spark);\\n%json x  \"}";
        //"x = bt.desc_stats(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        //"x = bt.desc_stats(\\\""+dir+"\\\",\\\""+cols+"\\\",sqlContext);\\n%json x  \"}";
        StringEntity entity = new StringEntity(json);
        httppost.setEntity(entity);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("X-Requested-By", "sharjain");
        System.out.println("statementid is running  " + sessionid);
        try {
            System.out.println("http info is  " + httppost.toString());
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("http response  is  " + response.toString());
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
        // return content;
        System.out.println("content is "+content);
        JSONParser parser = new JSONParser();
        JSONObject json_object = (JSONObject) parser.parse(content);
        Long statementId = (Long) json_object.get(AppConstants.ID);
        System.out.println("statementid: " + statementId);
        return statementId  .toString();
    }
	
	
	
	public String getBinStats(String sessionid,String path,String cols) throws Exception {
        System.out.println(sessionid);
        //TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
        String content=null;
        String dir=path+"*";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
        String json = "{\"code\": \"sc.addFile(\\\""+py_file+"\\\");\\n"
        		+ "import bavt_stats as bt;\\n"
        		+ "x = bt.bin_stats(\\\""+dir+"\\\","+cols+",spark);\\n%json x  \"}";
        //"x = bt.desc_stats(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        //"x = bt.desc_stats(\\\""+dir+"\\\",\\\""+cols+"\\\",sqlContext);\\n%json x  \"}";
        StringEntity entity = new StringEntity(json);
        httppost.setEntity(entity);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("X-Requested-By", "sharjain");
        System.out.println("statementid is running  " + sessionid);
        try {
            System.out.println("http info is  " + httppost.toString());
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("http response  is  " + response.toString());
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
        // return content;
        System.out.println("content is "+content);
        JSONParser parser = new JSONParser();
        JSONObject json_object = (JSONObject) parser.parse(content);
        Long statementId = (Long) json_object.get(AppConstants.ID);
        System.out.println("statementid: " + statementId);
        return statementId  .toString();
    }
	
	public String getCorrStats (String sessionid,String path,String cols) throws Exception {
        System.out.println(sessionid);
        TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
        String content=null;
        String dir=path+"*";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
        //String file="\\/Users\\/amitkumar647\\/Downloads\\/test\\/first.parquet";
        String json = "{\"code\": \"sc.addFile(\\\""+py_file+"\\\");\\n"
        		+ "import bavt_stats as bt;\\n"
        		+ "x = bt.corr_coeff(\\\""+dir+"\\\","+cols+",spark);\\n%json x  \"}";
        
        //"x = bt.corr_coeff(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        StringEntity entity = new StringEntity(json);
        httppost.setEntity(entity);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("X-Requested-By", "sharjain");
        System.out.println("statementid is running  " + sessionid);
        try {
            System.out.println("http info is  " + httppost.toString());
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("http response  is  " + response.toString());
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
        // return content;
        System.out.println("content is "+content);
        JSONParser parser = new JSONParser();
        JSONObject json_object = (JSONObject) parser.parse(content);
        Long statementId = (Long) json_object.get(AppConstants.ID);
        System.out.println("statementid: " + statementId);
        return statementId  .toString();
    }
	
	public String getLinearReg (String sessionid,String path, String cols,int tp,int vp) throws Exception {
        System.out.println(sessionid);
        TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
        String content=null;
        String dir=path+"*";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
        //String file="\\/Users\\/amitkumar647\\/Downloads\\/test\\/first.parquet";
        String json = "{\"code\": \"sc.addFile(\\\""+py_file+"\\\");\\n"
        		+ "import bavt_stats as bt;\\n"
        		+ "x = bt.lm_model(\\\""+dir+"\\\","+cols+","+tp+",spark);\\n%json x  \"}";
        
        //"x = bt.sklearn_reg_model(\\\""+dir+"\\\","+cols+","+tp+",sqlContext);\\n%json x  \"}";
        //"x = bt.desc_stats(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        //"x = bt.lm_model(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        
        StringEntity entity = new StringEntity(json);
        httppost.setEntity(entity);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("X-Requested-By", "sharjain");
        System.out.println("statementid is running  " + sessionid);
        try {
            System.out.println("http info is  " + httppost.toString());
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("http response  is  " + response.toString());
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
        // return content;
        System.out.println("content is "+content);
        JSONParser parser = new JSONParser();
        JSONObject json_object = (JSONObject) parser.parse(content);
        Long statementId = (Long) json_object.get(AppConstants.ID);
        System.out.println("statementid: " + statementId);
        return statementId  .toString();
    }
	
	
	
	public String getLinearReg1 (String sessionid,String path, String cols,String cols1,int tp,int vp) throws Exception {
        System.out.println(sessionid);
        TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
        String content=null;
        String dir=path+"*";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
        //String file="\\/Users\\/amitkumar647\\/Downloads\\/test\\/first.parquet";
        String json = "{\"code\": \"sc.addFile(\\\""+py_file+"\\\");\\n"
        		+ "import bavt_stats as bt;\\n"
        		+ "x = bt.lm_target_model(\\\""+dir+"\\\","+cols+","+cols1+","+tp+",spark);\\n%json x  \"}";
        
        //"x = bt.sklearn_reg_model(\\\""+dir+"\\\","+cols+","+tp+",sqlContext);\\n%json x  \"}";
        //"x = bt.desc_stats(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        //"x = bt.lm_model(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        
        StringEntity entity = new StringEntity(json);
        httppost.setEntity(entity);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("X-Requested-By", "sharjain");
        System.out.println("statementid is running  " + sessionid);
        try {
            System.out.println("http info is  " + httppost.toString());
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("http response  is  " + response.toString());
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
        // return content;
        System.out.println("content is "+content);
        JSONParser parser = new JSONParser();
        JSONObject json_object = (JSONObject) parser.parse(content);
        Long statementId = (Long) json_object.get(AppConstants.ID);
        System.out.println("statementid: " + statementId);
        return statementId  .toString();
    }
	
	
	
	public String getPrediction (String sessionid,String path, String cols,String cols1,int tp,int vp) throws Exception {
        System.out.println(sessionid);
        TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
        String content=null;
        String dir=path+"*";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
        //String file="\\/Users\\/amitkumar647\\/Downloads\\/test\\/first.parquet";
        String json = "{\"code\": \"sc.addFile(\\\""+py_file+"\\\");\\n"
        		+ "import bavt_stats as bt;\\n"
        		+ "x = bt.prediction_test(\\\""+dir+"\\\","+cols+","+cols1+","+tp+",spark);\\n%json x  \"}";
        
        //"x = bt.sklearn_reg_model(\\\""+dir+"\\\","+cols+","+tp+",sqlContext);\\n%json x  \"}";
        //"x = bt.desc_stats(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        //"x = bt.lm_model(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        
        StringEntity entity = new StringEntity(json);
        httppost.setEntity(entity);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("X-Requested-By", "sharjain");
        System.out.println("statementid is running  " + sessionid);
        try {
            System.out.println("http info is  " + httppost.toString());
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("http response  is  " + response.toString());
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
        // return content;
        System.out.println("content is "+content);
        JSONParser parser = new JSONParser();
        JSONObject json_object = (JSONObject) parser.parse(content);
        Long statementId = (Long) json_object.get(AppConstants.ID);
        System.out.println("statementid: " + statementId);
        return statementId  .toString();
    }
	
	public String getSKLearnReg (String sessionid,String path, String cols,int tp,int vp) throws Exception {
        System.out.println(sessionid);
        TimeUnit.SECONDS.sleep(AppConstants.LIVY_POST_HOLD);
        String content=null;
        String dir=path+"*";
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://"+AppConstants.LIVY_HOSTNAME+":"+AppConstants.LIVY_PORT+"/sessions/"+sessionid+"/statements");
        //String file="\\/Users\\/amitkumar647\\/Downloads\\/test\\/first.parquet";
        String json = "{\"code\": \"sc.addFile(\\\""+py_file+"\\\");\\n"
        		+ "import bavt_stats as bt;\\n"
        		+ "x = bt.sklearn_reg_model(\\\""+dir+"\\\","+cols+","+tp+",spark);\\n%json x  \"}";
        
        //"x = bt.sklearn_reg_model(\\\""+dir+"\\\","+cols+","+tp+",sqlContext);\\n%json x  \"}";
        //"x = bt.desc_stats(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        //"x = bt.lm_model(\\\""+dir+"\\\","+cols+",sqlContext);\\n%json x  \"}";
        
        StringEntity entity = new StringEntity(json);
        httppost.setEntity(entity);
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("X-Requested-By", "sharjain");
        System.out.println("statementid is running  " + sessionid);
        try {
            System.out.println("http info is  " + httppost.toString());
            HttpResponse response = httpclient.execute(httppost);
            System.out.println("http response  is  " + response.toString());
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
        // return content;
        System.out.println("content is "+content);
        JSONParser parser = new JSONParser();
        JSONObject json_object = (JSONObject) parser.parse(content);
        Long statementId = (Long) json_object.get(AppConstants.ID);
        System.out.println("statementid: " + statementId);
        return statementId  .toString();
    }

}
