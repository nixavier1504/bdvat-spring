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
public class datacomparison {
	
	private String username;
    
	private String dataset1;
	private String dataset2;
    private String columnss;
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
	public String getDataset1() {
		return dataset1;
	}
	public void setDataset1(String dataset1) {
		this.dataset1 = dataset1;
	}
	public String getDataset2() {
		return dataset2;
	}
	public void setDataset2(String dataset2) {
		this.dataset2 = dataset2;
	}
	public String getColumnss() {
		return columnss;
	}
	public void setColumnss(String columnss) {
		this.columnss = columnss;
	}
}
