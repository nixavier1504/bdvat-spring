package com.dvenci.service;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dvenci.livy.service.LivyService;
import com.dvenci.livy.utils.LivyUtils;
import com.dvenci.repository.AnalysisMethods;
import com.dvenci.utils.AnalysisUtils;

@Service
public class AnalysisService implements AnalysisMethods{
	
	@Autowired
	private LivyService livy;
	
	@Value("${python.filepath}")
	private String pythonFile;
	
	@Override
	public JSONArray getSchema(Long sessionId, String dirPath) throws IOException, ParseException, InterruptedException {
		String dir = dirPath + "*";
		String scalaCode= "import org.apache.spark.sql.SQLContext;"
				+ "import scala.util.parsing.json.JSONObject;"
				+ "def schemaParquet(path: String): String={"
				+ "val sqlContext = new SQLContext(sc);"
				+ "val schemaFile = sqlContext.read.parquet(path.toString).dtypes.toMap;"
				+ "return JSONObject(schemaFile).toString() };"
				+ "val schema = schemaParquet(\""+dir+"\") \n%json schema";
		Long statementId = livy.createStatement(sessionId, scalaCode);
		JSONObject response = livy.getStatementResponse(sessionId, statementId);
		JSONObject json = (JSONObject) LivyUtils.getResultFromResponse(response);
		JSONArray schemaArray = AnalysisUtils.processSchema(json);
		return schemaArray;
	}
	
	@Override
	public JSONArray getDescriptiveAnalysis(Long sessionId, String dirPath, String cols) throws IOException, ParseException, InterruptedException {
		String dir = dirPath + "*";

		String scalaCode = "sc.addFile(\"" + pythonFile + "\");\n"
				+ "import bavt_stats as bt;\n"
				+ "x = bt.desc_stats(\"" + dir + "\"," + cols + ",spark);\n"
				+ "%json x";
		Long statementId = livy.createStatement(sessionId, scalaCode);
		JSONObject response = livy.getStatementResponse(sessionId, statementId);
		JSONObject json = (JSONObject) LivyUtils.getResultFromResponse(response);
		JSONArray statsArray = AnalysisUtils.processDescStats(json);
		return statsArray;
	}
	
	@Override
	public JSONObject getCorrelation(Long sessionId, String dirPath, String cols) throws IOException, ParseException, InterruptedException {
		String dir = dirPath + "*";

		String scalaCode = "sc.addFile(\"" + pythonFile + "\");\n"
				+ "import bavt_stats as bt;\n"
				+ "x = bt.corr_coeff(\"" + dir + "\"," + cols + ",spark);\n"
				+ "%json x";
		Long statementId = livy.createStatement(sessionId, scalaCode);
		JSONObject response = livy.getStatementResponse(sessionId, statementId);
		JSONObject json = (JSONObject) LivyUtils.getResultFromResponse(response);
		return json;
	}

	@Override
	public JSONArray getPredictiveAnalysis(Long sessionId, String dirPath, String cols, int trainingPercent) throws IOException, ParseException, InterruptedException {
		String dir = dirPath + "*";

		String scalaCode = "sc.addFile(\"" + pythonFile + "\");\n"
				+ "import bavt_stats as bt;\n"
				+ "x = bt.lm_model(\"" + dir + "\"," + cols + ", " + trainingPercent + " ,spark);\n"
				+ "%json x";
		Long statementId = livy.createStatement(sessionId, scalaCode);
		JSONObject response = livy.getStatementResponse(sessionId, statementId);
		JSONArray json = (JSONArray) LivyUtils.getResultFromResponse(response);
		return json;
	}

	@Override
	public JSONObject getNullCount(Long sessionId, String dirPath, String col) throws IOException, ParseException, InterruptedException {
		String dir = dirPath + "*";
		String scalaCode= "import org.apache.spark.sql.SQLContext;"
				+ "import scala.util.parsing.json.JSONObject;"
				+ "def schemaParquet(path: String,col: String): String={"
				+ "val sqlContext = new SQLContext(sc);"
				+ "val schemaFile_temp = sqlContext.read.parquet(path.toString);"
				+ "val schemaFile = schemaFile_temp.filter(schemaFile_temp(col).isNull || schemaFile_temp(col) === \"\").count;" 
				+ "return schemaFile.toString() };"
				+ "val schema = schemaParquet(\"" + dir + "\",\"" + col + "\") \n%json schema";
		Long statementId = livy.createStatement(sessionId, scalaCode);
		JSONObject response = livy.getStatementResponse(sessionId, statementId);
		Long nullCount = (Long) LivyUtils.getResultFromResponse(response);
		JSONObject nullCountObj = AnalysisUtils.processNullCount(col, nullCount);
		return nullCountObj;
	}

	@Override
	public JSONObject getBinStats(Long sessionId, String dirPath, String col) throws IOException, ParseException, InterruptedException {
		String dir = dirPath + "*";

		String scalaCode = "sc.addFile(\"" + pythonFile + "\");\n"
				+ "import bavt_stats as bt;\n"
				+ "x = bt.bin_stats(\"" + dir + "\"," + col + ",spark);\n"
				+ "%json x";
		Long statementId = livy.createStatement(sessionId, scalaCode);
		JSONObject response = livy.getStatementResponse(sessionId, statementId);
		JSONObject json = (JSONObject) LivyUtils.getResultFromResponse(response);
		JSONObject statsObj = AnalysisUtils.processBinStats(json);
		return statsObj;
	}

	@Override
	public JSONArray getCompStats(Long sessionId, String dirPath1, String dirPath2, String col) throws IOException, ParseException, InterruptedException {
		String dir1 = dirPath1 + "*";
		String dir2 = dirPath2 + "*";
		String scalaCode= "import org.apache.spark.sql.SQLContext;\r\n" + 
				"import scala.util.parsing.json.JSONObject;\r\n" + 
				"def schemaParquet(path1: String,path2: String,col: String): String={\r\n" + 
				"	val sqlContext = new SQLContext(sc);\r\n" + 
				"	val dataset1 = sqlContext.read.parquet(path1);\r\n" + 
				"	val dataset2 = sqlContext.read.parquet(path2);\r\n" + 
				"	dataset1.registerTempTable(\"dataset11\");\r\n" + 
				"	dataset2.registerTempTable(\"dataset22\");\r\n" + 
				"	val d1= dataset1.select(col);\r\n" + 
				"	val d2= dataset2.select(col);\r\n" + 
				"	val d3=d1.except(d2).count();\r\n" + 
				"	val d4=d2.except(d1).count();\r\n" + 
				"	return \"[\" + d3.toString + \",\" + d4.toString + \"]\"\r\n" + 
				"};" + 
				"val schema = schemaParquet(\"" + dir1 + "\",\"" + dir2 + "\",\"" + col + "\") \n%json schema";
		
		Long statementId = livy.createStatement(sessionId, scalaCode);
		JSONObject response = livy.getStatementResponse(sessionId, statementId);
		JSONArray compStatObj = (JSONArray) LivyUtils.getResultFromResponse(response);
		return compStatObj;
	}
	
	public JSONObject getFeatureEngineering(Long sessionId, String dataset, String cols, String targetCol, int trainingPercent) throws IOException, ParseException, InterruptedException {
		String dir = dataset + "*";
		String scalaCode = "sc.addFile(\"" + pythonFile + "\");\n"
				+ "import bavt_stats as bt;\n"
				+ "x = bt.lm_target_model(\"" + dir + "\"," + cols + ",\"" + targetCol + "\"," + trainingPercent + " ,spark);\n"
				+ "%json x";
		Long statementId = livy.createStatement(sessionId, scalaCode);
		JSONObject response = livy.getStatementResponse(sessionId, statementId);
		JSONArray json = (JSONArray) LivyUtils.getResultFromResponse(response);
		JSONObject result = AnalysisUtils.processFeatureEngineering(json);
		return result;
	}
	
	public JSONObject getTargetPrediction(Long sessionId, String dataset, String cols, String targetCol, int trainingPercent) throws IOException, ParseException, InterruptedException {
		String dir = dataset + "*";
		String scalaCode = "sc.addFile(\"" + pythonFile + "\");\n"
				+ "import bavt_stats as bt;\n"
				+ "x = bt.prediction_test(\"" + dir + "\"," + cols + ",\"" + targetCol + "\"," + trainingPercent + " ,spark);\n"
				+ "%json x";
		Long statementId = livy.createStatement(sessionId, scalaCode);
		JSONObject response = livy.getStatementResponse(sessionId, statementId);
//		JSONArray json = (JSONArray) LivyUtils.getResultFromResponse(response);
//		JSONObject result = AnalysisUtils.processFeatureEngineering(json);
		return response;
	}


}
