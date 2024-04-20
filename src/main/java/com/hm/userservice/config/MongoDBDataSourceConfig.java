package com.hm.userservice.config;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.hm.userservice.mongoconfig.dao", mongoTemplateRef = "mongoTemplate")
public class MongoDBDataSourceConfig {

	MongoProperties properties;

	@Bean("properties")
	@ConfigurationProperties(prefix = "spring.mongoconfig.datasource")
	public MongoProperties mongoProperties() {
		if (properties == null) {
			return new MongoProperties();
		}
		return properties;
	}
	
	@Bean
	public MongoClient mongoClient() {
		properties = mongoProperties();
		return MongoClients.create(properties.getUri());
	}
	
	@Bean
	public MongoTemplate mongoTemplate() {
		properties = mongoProperties();
		return new MongoTemplate(mongoClient(), properties.getDatabase());
	}
}
