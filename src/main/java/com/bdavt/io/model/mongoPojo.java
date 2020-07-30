/**
 * Author: Amit Kumar
 * amitkumar647
 * 5:39:50 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.model;

/**
 * @author amitkumar647
 *
 */
public class mongoPojo {
	
	private long uid;
	private String executionid;
	private String username;
    private String dataset;
    private String Data;
    private String timestamp;
    private String mode;
    private String profile;
    
    public mongoPojo() {

	}
    
    public mongoPojo(long uid,String executionid,String username, String dataset,String Data, String timestamp, String mode, String profile) {
    	this.uid=uid;
    	this.executionid=executionid;
		this.username = username;
		this.dataset = dataset;
		this.Data = Data;
		this.timestamp = timestamp;
		this.mode = mode;
		this.profile = profile;
	}
    
    public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getEid() {
		return executionid;
	}
	public void setEid(String executionid) {
		this.executionid = executionid;
	}
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		this.Data = data;
	}

	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String time) {
		this.timestamp = time;
	}
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
}
