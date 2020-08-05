package com.dvenci.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.BinStatsHistory;

@Repository
public interface BinStatsHistoryRepo extends MongoRepository<BinStatsHistory, String> {

}
