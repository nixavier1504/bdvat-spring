/**
 * Author: Amit Kumar
 * amitkumar647
 * 5:49:45 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.model;

/**
 * @author amitkumar647
 *
 */
public class linearUser {
	private String username;
	private String dataset;
	private String dependent_var;
	private String independent_var;

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
	public String getDependent() {
		return dependent_var;
	}
	public void setDependent(String dependent_var) {
		this.dependent_var = dependent_var;
	}
	public String getInDependent() {
		return independent_var;
	}
	public void setInDependent(String independent_var) {
		this.independent_var = independent_var;
	}

}
