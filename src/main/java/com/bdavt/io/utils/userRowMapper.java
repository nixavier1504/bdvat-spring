/**
 * Author: Amit Kumar
 * amitkumar647
 * 2:44:36 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.utils;

/**
 * @author amitkumar647
 *
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.bdavt.io.model.userConfig;

public class userRowMapper implements RowMapper<userConfig> {

	@Override
	public userConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		userConfig usr = new userConfig();
		usr.setId(rs.getLong("id"));
		usr.setusername(rs.getString("username"));
		usr.setsource(rs.getString("source"));
		usr.setdataset(rs.getString("dataset"));
		usr.setKerb(rs.getString("kerb"));
		return usr;
	}

}
