package com.dvenci.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.TargetPredictHistory;

@Repository
public interface TargetPredictHistoryRepo extends MongoRepository<TargetPredictHistory, String>{

}
