package com.dvenci.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.NullCountHistory;

@Repository
public interface NullCountHistoryRepo extends MongoRepository<NullCountHistory, String>{
	
}
