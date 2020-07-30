/**
 * Author: Amit Kumar
 * amitkumar647
 * 6:24:46 pm
 * BDAVT-Psql-Spring
 */
package com.bdavt.io.repository;

/**
 * @author amitkumar647
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bdavt.io.model.configJson;

@Repository
public interface ConfigRepository extends JpaRepository<configJson, Long>{

}
