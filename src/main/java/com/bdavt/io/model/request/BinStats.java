package com.bdavt.io.model.request;

public class BinStats {
	
	private String username;
	private String dataset;
    private String columns;
    private String profile;
    private String Exec_id;
    
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
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getExec_id() {
		return Exec_id;
	}
	public void setExec_id(String exec_id) {
		Exec_id = exec_id;
	}
    
}
