package com.bdavt.io.mongo;

import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.DB;

@Configuration
public class SimpleMongoConfig {

	@Value("${mongo.host}")
	private String MONGO_HOST;
	
	@Value("${mongo.port}")
	private int MONGO_PORT;
	
	@Value("${mongo.db}")
	private String MONGO_DB;
	
	@Bean
    public MongoClient mongo() throws UnknownHostException {
		System.out.println("Connecting to " + MONGO_DB + " on " + MONGO_HOST + ":" + MONGO_PORT);
		return new MongoClient(MONGO_HOST, MONGO_PORT);
    }	
	
	@Bean
	public DB mongoDb() throws UnknownHostException {
		return mongo().getDB(MONGO_DB);
	}
}
