/**
 * Author: Amit Kumar
 * amitkumar647
 * 6:16:11 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.model;

/**
 * @author amitkumar647
 *
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_json")
public class configJson {

	//private long id;
	private String username;
	private String profile;
	private String config1;
	private String config2;
	private String config3;

	public configJson() {

	}

	public configJson(String username, String profile,String config_1, String config_2, String config_3) {
		this.username = username;
		this.profile = profile;
		this.config1 = config_1;
		this.config2 = config_2;
		this.config3 = config_3;
	}

	@Id
	public String getusername() {
		return username;
	}
	public void setusername(String username) {
		this.username = username;
	}
	
	public String getprofile() {
		return profile;
	}
	public void setprofile(String profile) {
		this.profile = profile;
	}
	
	/*
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	*/
	
	@Column(name = "config_1", nullable = true)
	public String getconfig_1() {
		return config1;
	}
	public void setconfig_1(String config_1) {
		this.config1 = config_1;
	}

	@Column(name = "config_2", nullable = true)
	public String getconfig_2() {
		return config2;
	}
	public void setconfig_2(String config_2) {
		this.config2 = config_2;
	}

	@Column(name = "config_3", nullable = true)
	public String getconfig_3() {
		return config3;
	}
	public void setconfig_3(String config_3) {
		this.config3 = config_3;
	}

	@Override
	public String toString() {
		return "Config [username=" + username + ", profile= "+profile+", config1=" + config1 + ", config2=" + config2 + ", config3=" + config3
				+ "]";
	}

}