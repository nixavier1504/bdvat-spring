/**
 * Author: Amit Kumar
 * amitkumar647
 * 6:20:13 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.controller;

/**
 * @author amitkumar647
 *
 */

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bdavt.io.constants.AppConstants;
import com.bdavt.io.exception.ResourceNotFoundException;
import com.bdavt.io.model.configJson;
import com.bdavt.io.model.datacomparison;
import com.bdavt.io.model.directoryPath;
import com.bdavt.io.model.linearUser;
import com.bdavt.io.model.mongoQuery;
import com.bdavt.io.model.statistics;
import com.bdavt.io.model.statistics2;
import com.bdavt.io.model.userConfig;
import com.bdavt.io.model.userSchema;
import com.bdavt.io.model.userSchemanullcount;
import com.bdavt.io.repository.postgresFetch;
import com.bdavt.io.service.PostgresDAO;
import com.bdavt.io.service.livyFileServiceImpl;
import com.bdavt.io.service.livySchemaServiceImpl;
import com.bdavt.io.utils.mongoDBCrud;


@RestController
@RequestMapping(value ="/bdavt/v1",produces = "application/json")
public class Controller {
    @Autowired
	private PostgresDAO postgresDAO;
    
    @Autowired
    private livyFileServiceImpl livyfile; 
    
    @Autowired
    private livySchemaServiceImpl livyschema;
    
    @Autowired
    private postgresFetch postFetch;

    @CrossOrigin(origins = "*")
    @GetMapping("/config")
    public List<configJson> getAllConfig() {
        return postgresDAO.getAllConfig();
    }
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/configList")
    public List<userConfig> getAllUserConfig() {
    return postFetch.findAll();
    }
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/configUser")
    public List<userConfig> createEmployee(@RequestBody userConfig usr) {
    	return postFetch.getUser(usr);
    }
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/createUser")
    public void createUser(@RequestBody userConfig usr) {
    	postFetch.insertUser(usr);
    }
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/updateUser")
    public void updateUsr(@RequestBody userConfig usr) {
    	postFetch.updateUser(usr);
    }
    
    //Postgres OLD Starts
    @CrossOrigin(origins = "*")
    @GetMapping("/config/{id}")
    public ResponseEntity<configJson> getConfigId(@PathVariable(value = "id") Long configId)
        throws ResourceNotFoundException {     
        return postgresDAO.getConfigById(configId);
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/config")
    public configJson createConfig(@Valid @RequestBody configJson ConfigJson) {
        return postgresDAO.createConfig(ConfigJson);
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/config/{id}")
    public ResponseEntity<configJson> updateConfig(@PathVariable(value = "id") Long configId,
         @Valid @RequestBody configJson configDetails) throws ResourceNotFoundException {
        return postgresDAO.updateConfig(configId, configDetails);
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/config/{id}")
    public Map<String, Boolean> deleteConfig(@PathVariable(value = "id") Long configId)
         throws ResourceNotFoundException {
        return postgresDAO.deleteConfig(configId);
    }
    //Postgres OLD ends
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/livy/schematest")
	public JSONArray getSchema(@RequestParam String path) throws Exception {
		return livyschema.process(path,AppConstants.GETSCHEMA);
	}
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/livy/schema")
    public JSONArray getSchema(@RequestBody userSchema path) throws Exception {
    	return livyschema.process(path.getUsername(),path.getDataset(),path.getProfile(),AppConstants.GETSCHEMA);
	}
    
    
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/livy/nullcount")
    public JSONArray getSchema1(@RequestBody userSchemanullcount path) throws Exception {
    	return livyschema.process1(path.getUsername(),path.getDataset(),path.getProfile(),path.getColumnss(),AppConstants.NULLCOUNT);
	}
    
    
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/compstats")
    public JSONObject getcompStats(@RequestBody datacomparison path) throws Exception{
    	//System.out.println(path.getUsername()+" "+path.getDataset()+" "+path.getColumns()+" "+path.getProfile()+" "+path.getExec_id());
    	return livyschema.process5(path.getUsername(),path.getDataset1(),path.getDataset2(),path.getColumnss(),AppConstants.NULLCOUNT);
    }
    
    
    
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/filetest")
    public JSONObject getFileNames(@RequestParam String path) throws Exception{
    	return livyfile.process(path,AppConstants.LISTFILE);
    }
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/file")
    public JSONObject getFileNames(@RequestBody   directoryPath path) throws Exception{
    	return livyfile.process(path.getUsername(),path.getDirectory_path(),AppConstants.LISTFILE);
    }
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/getstats")
    public JSONObject getDescriptiveStats(@RequestBody statistics path) throws Exception{
    	//System.out.println(path.getUsername()+" "+path.getDataset()+" "+path.getColumns()+" "+path.getProfile()+" "+path.getExec_id());
       return livyschema.process2(path,AppConstants.GETDESC);
    }
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/CorrStats")
    public JSONObject getCorrStats(@RequestBody statistics path) throws Exception{
        return livyschema.process2(path,AppConstants.GETCORR);
    }
    
    
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/PreStats")
    public JSONArray getPreStats(@RequestBody statistics path) throws Exception{
        return livyschema.process3(path,AppConstants.GETPRED);
    }
    
    
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/PreFEStats")
    public JSONArray getPreFEStats(@RequestBody statistics2 path) throws Exception{
        return livyschema.process33(path,AppConstants.GETPRED);
    }
    
    
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/PredictTarget")
    public JSONArray getPredictTarget(@RequestBody statistics2 path) throws Exception{
        return livyschema.predicttarget(path,AppConstants.GETPRED);
    }
    
    
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/PreStatsSK")
    public JSONArray getPreStatsSK(@RequestBody statistics path) throws Exception{
        return livyschema.process3(path,AppConstants.GETPREDSK);
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping(value="/mongo/getMetadata")
    public JSONArray getHistoryMetaData(@RequestParam String user) throws Exception{
        return mongoDBCrud.dbGet(user);
    }
    @CrossOrigin(origins = "*")
    @GetMapping(value="/mongo/getdatatest")
    public JSONArray getHistoryDataTest(@RequestParam long uid) throws Exception{
        return mongoDBCrud.dbGetData(uid);
    }
    @CrossOrigin(origins = "*")
    @PostMapping(value="/mongo/getdata")
    public JSONArray getHistoryData(@RequestBody mongoQuery uid) throws Exception{
    	//System.out.println(uid.getUsername()+" "+uid.getExec_id());
        return mongoDBCrud.dbGetData(uid.getUsername(),uid.getExec_id());
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping(value="/livy/binstats")
    public JSONObject getbinStats(@RequestBody statistics path) throws Exception{
    	//System.out.println(path.getUsername()+" "+path.getDataset()+" "+path.getColumns()+" "+path.getProfile()+" "+path.getExec_id());
       return livyschema.process4(path,AppConstants.GETBINSTATS);
    }
    
    
}
