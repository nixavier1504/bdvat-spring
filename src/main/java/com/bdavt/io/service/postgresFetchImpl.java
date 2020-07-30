/**
 * Author: Amit Kumar
 * amitkumar647
 * 2:40:56 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bdavt.io.model.userConfig;
import com.bdavt.io.repository.postgresFetch;
import com.bdavt.io.utils.userRowMapper;

/**
 * @author amitkumar647
 *
 */
@Repository
public class postgresFetchImpl implements postgresFetch{

	public postgresFetchImpl(NamedParameterJdbcTemplate template) {  
		this.template = template;  
	} 

	NamedParameterJdbcTemplate template; 
	@Override
	public List<userConfig> findAll() {
		// TODO Auto-generated method stub
		return template.query("select id,username,source,kerb,json_agg(trim(dataset)) as dataset FROM config_json_x group by username,source,id,kerb", new userRowMapper());
	}

	@Override
	public void insertUser(userConfig usr) {
		// TODO Auto-generated method stub
		final String sql = "insert into config_json_x(username, source ,dataset, kerb) values(:username,:source,:dataset,:kerb)";
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("username", usr.getusername())
				.addValue("source", usr.getsource())
				.addValue("dataset", usr.getdataset())
				.addValue("kerb", usr.getKerb());
		template.update(sql,param, holder);

	}

	@Override
	public void updateUser(userConfig usr) {
		// TODO Auto-generated method stub
		final String sql = "update config_json_x set source=:source, dataset=:dataset, kerb=:kerb where username=:username";
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("username", usr.getusername())
				.addValue("source", usr.getsource())
				.addValue("dataset", usr.getdataset())
				.addValue("kerb", usr.getKerb());
		template.update(sql,param, holder);

	}

	@Override
	public List<userConfig> getUser(userConfig usr) {
		// TODO Auto-generated method stub
		String username=usr.getusername().toString();
		final String sql = "select id,username,source,kerb,json_agg(trim(dataset)) as dataset FROM config_json_x where username='"+ username +"' group by username,source,id,kerb";
		return template.query(sql.trim(), new userRowMapper());
	}

	@Override
	public void deleteUser(userConfig usr) {
		// TODO Auto-generated method stub
		final String sql = "delete from config_json_x where username=:username";
		Map<String,Object> map=new HashMap<String,Object>();  
		map.put("username", usr.getusername());
		template.execute(sql,map,new PreparedStatementCallback<Object>() {  
			@Override  
			public Object doInPreparedStatement(PreparedStatement ps)  
					throws SQLException, DataAccessException {  
				return ps.executeUpdate();  
			}  
		}); 

	}

	@Override
	public void executeUpdateUser(userConfig usr) {
		// TODO Auto-generated method stub

		final String sql = "update config_json_x set source=:source, dataset=:dataset,kerb=:kerb where username=:username";
		Map<String,Object> map=new HashMap<String,Object>();  
		map.put("username", usr.getusername());
		map.put("source", usr.getsource());
		map.put("dataset", usr.getdataset());
		map.put("kerb", usr.getKerb());
		template.execute(sql,map,new PreparedStatementCallback<Object>() {  
			@Override  
			public Object doInPreparedStatement(PreparedStatement ps)  
					throws SQLException, DataAccessException {  
				return ps.executeUpdate();  
			}  
		});  

	}

}
