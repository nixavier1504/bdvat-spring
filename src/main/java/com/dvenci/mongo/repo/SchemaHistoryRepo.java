package com.dvenci.mongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dvenci.mongo.model.SchemaHistory;

@Repository
public interface SchemaHistoryRepo extends MongoRepository<SchemaHistory, Long>{

}
