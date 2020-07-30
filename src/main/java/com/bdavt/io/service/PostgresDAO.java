/**
 * Author: Amit Kumar
 * amitkumar647
 * 1:38:03 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.bdavt.io.exception.ResourceNotFoundException;
import com.bdavt.io.model.configJson;
import com.bdavt.io.repository.ConfigRepository;

/**
 * @author amitkumar647
 *
 */
@Service
public class PostgresDAO{

	@Autowired
	private ConfigRepository configRepository;

	public List<configJson> getAllConfig() {
        return configRepository.findAll();
    }
	
	public configJson createConfig(configJson ConfigJson) {
        return configRepository.save(ConfigJson);
    }
	
	public ResponseEntity<configJson> getConfigById(Long configId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		configJson cnf = null;
		try {
			cnf = configRepository.findById(configId)
					.orElseThrow(() -> new ResourceNotFoundException("Config not found for this id :: " + configId));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(cnf);
	}

	public ResponseEntity<configJson> updateConfig(Long configId,configJson configDetails) throws ResourceNotFoundException {
		
		configJson cnf = configRepository.findById(configId)
				.orElseThrow(() -> new ResourceNotFoundException("Config not found for this id :: " + configId));

		cnf.setconfig_3(configDetails.getconfig_3());
		cnf.setconfig_2(configDetails.getconfig_2());
		cnf.setconfig_1(configDetails.getconfig_1());
		final configJson updatedConfig = configRepository.save(cnf);
		return ResponseEntity.ok(updatedConfig);
	}
	
	public Map<String, Boolean> deleteConfig(Long configId)
	         throws ResourceNotFoundException {
	        configJson cnf = configRepository.findById(configId)
	       .orElseThrow(() -> new ResourceNotFoundException("Config not found for this id :: " + configId));

	        configRepository.delete(cnf);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }

}
