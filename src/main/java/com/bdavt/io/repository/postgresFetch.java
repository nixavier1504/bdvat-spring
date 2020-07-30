/**
 * Author: Amit Kumar
 * amitkumar647
 * 2:17:40 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.repository;

import java.util.List;

import com.bdavt.io.model.userConfig;
/**
 * @author amitkumar647
 *
 */
public interface postgresFetch {
	
	List<userConfig> findAll();
	List<userConfig> getUser(userConfig usr);
	void insertUser(userConfig usr);
	void updateUser(userConfig usr);
	void executeUpdateUser(userConfig usr);
	public void deleteUser(userConfig usr);

}
