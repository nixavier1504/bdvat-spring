/**
 * Author: Amit Kumar
 * amitkumar647
 * 2:57:35 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.model;

import java.util.List;

/**
 * @author amitkumar647
 *
 */
public class filePathBean {
	private String Pathoffile;
	private String formvalue;
	private List<String> finalvalue;



	public String getPathOfFile() {
		return Pathoffile;
	}

	public void setPathOfFile(String pathoffile) {
		Pathoffile = pathoffile;
	}

	public String getFormValue() {
		return formvalue;
	}

	public void setFormValue(String formvalue) {
		this.formvalue = formvalue;
	}

	public List<String> getFinalValue() {
		return finalvalue;
	}

	public void setFinalValue(List<String> finalvalue) {
		this.finalvalue = finalvalue;
	}

}
