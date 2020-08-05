package com.dvenci.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.DescStatsHistory;

@Repository
public interface DescStatsHistoryRepo extends MongoRepository<DescStatsHistory, String>{

}
