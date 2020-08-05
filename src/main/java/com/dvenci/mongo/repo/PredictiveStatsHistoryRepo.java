package com.dvenci.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.PredictiveStatsHistory;

@Repository
public interface PredictiveStatsHistoryRepo extends MongoRepository<PredictiveStatsHistory, String>{

}
