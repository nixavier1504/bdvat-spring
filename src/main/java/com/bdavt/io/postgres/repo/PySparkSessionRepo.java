package com.bdavt.io.postgres.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bdavt.io.postgres.model.PySparkSession;

@Repository
public interface PySparkSessionRepo extends JpaRepository<PySparkSession, Long>{

	@Query(value = "SELECT S from PySparkSession S WHERE S.username = ?1")
	public PySparkSession getSessionByUserName(String username);
}
