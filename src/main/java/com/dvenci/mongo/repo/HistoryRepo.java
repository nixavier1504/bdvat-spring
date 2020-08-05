package com.dvenci.mongo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.AnalysisHistory;

@Repository
public interface HistoryRepo extends MongoRepository<AnalysisHistory, String> {
	
	@Query(value = "{'username': ?0}")
	List<AnalysisHistory> getHistoryByUser(String username);
	
	@Query(value = "{'username': ?0}", fields = "{'timestamp':1}")
	List<AnalysisHistory> getTimeStampsByUsername(String username);
	
}
