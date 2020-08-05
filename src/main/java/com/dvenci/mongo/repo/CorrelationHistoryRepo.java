package com.dvenci.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.CorrelationHistory;

@Repository
public interface CorrelationHistoryRepo extends MongoRepository<CorrelationHistory, String>{

}
