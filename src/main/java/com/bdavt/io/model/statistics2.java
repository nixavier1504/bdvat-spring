/**
 * Author: Amit Kumar
 * amitkumar647
 * 1:27:59 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.model;

/**
 * @author amitkumar647
 *
 */
public class statistics2 {
	
	private String username;
    
	private String dataset;
    private String columns;
    private String columns1;
    
	private String profile;
    private String Exec_id;
	private int thresholdPercantage;
	private int validationPercentage;
    
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getColumns1() {
		return columns1;
	}
	public void setColumns1(String columns1) {
		this.columns1 = columns1;
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
	public void setExec_id(String Exec_id) {
		this.Exec_id = Exec_id;
	}
	/**
	 * @return the thresholdPercantage
	 */
	public int getThresholdPercantage() {
		return thresholdPercantage;
	}
	/**
	 * @param thresholdPercantage the thresholdPercantage to set
	 */
	public void setThresholdPercantage(int thresholdPercantage) {
		this.thresholdPercantage = thresholdPercantage;
	}
	/**
	 * @return the validationPercentage
	 */
	public int getValidationPercentage() {
		return validationPercentage;
	}
	/**
	 * @param validationPercentage the validationPercentage to set
	 */
	public void setValidationPercentage(int validationPercentage) {
		this.validationPercentage = validationPercentage;
	}
}
