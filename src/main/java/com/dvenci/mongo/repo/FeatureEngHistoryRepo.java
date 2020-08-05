package com.dvenci.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.FeatureEngHistory;

@Repository
public interface FeatureEngHistoryRepo extends MongoRepository<FeatureEngHistory, String>{

}
