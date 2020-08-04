package com.bdavt.io.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.DB;

@Configuration
public class SimpleMongoConfig {

	@Value("${mongo.host}")
	private String MONGO_HOST;
	
	@Value("${mongo.port}")
	private int MONGO_PORT;
	
	@Value("${mongo.user}")
	private String MONGO_USER;
	
	@Value("${mongo.password}")
	private String MONGO_PASSWORD;
	
	@Value("${mongo.db}")
	private String MONGO_DB;
	
	@Value("${mongo.uri}")
	private String MONGO_URI;
	
	@Bean
    public MongoClient mongo() throws UnknownHostException {
		System.out.println("Connecting to " + MONGO_DB + " on " + MONGO_HOST + ":" + MONGO_PORT);
		
//		ServerAddress server = new ServerAddress(MONGO_HOST, MONGO_PORT);
//		List<MongoCredential> credentialList = new ArrayList<MongoCredential>();
//		credentialList.add(MongoCredential.createMongoCRCredential(MONGO_USER, MONGO_DB, MONGO_PASSWORD.toCharArray()));
//		return new MongoClient(server, credentialList);
		
//		MongoClientURI uri = new MongoClientURI(MONGO_URI);
//		return new MongoClient(uri);
		
		return new MongoClient(MONGO_HOST, MONGO_PORT);
    }	
	
	@Bean
	public DB mongoDb() throws UnknownHostException {
		return mongo().getDB(MONGO_DB);
	}
}
