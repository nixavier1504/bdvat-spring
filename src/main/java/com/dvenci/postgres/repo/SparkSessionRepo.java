package com.dvenci.postgres.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dvenci.postgres.model.SparkSession;

@Repository
public interface SparkSessionRepo extends JpaRepository<SparkSession, Long>{

	@Query(value = "SELECT S from SparkSession S WHERE S.username = ?1")
	public SparkSession getSessionByUserName(String username);
}
