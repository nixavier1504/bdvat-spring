package com.dvenci.mongo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.MHistory;

@Repository
public interface HistoryRepo extends MongoRepository<MHistory, String> {
	
	@Query(value = "{'username': ?0}")
	List<MHistory> getHistoryByUser(String username);
	
	@Query(value = "{'username': ?0}", fields = "{'timestamp':1}")
	List<MHistory> getTimeStampsByUsername(String username);
	
}
