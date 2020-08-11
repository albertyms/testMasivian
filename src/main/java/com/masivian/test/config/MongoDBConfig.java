package com.masivian.test.config;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
public abstract class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String database;
    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private String port;
    @Value("${spring.data.mongodb.username}")
    private String username;
    @Value("${spring.data.mongodb.password}")
    private String password;

    @Override
    public MongoClient mongoClient() {
        ServerAddress serverAddress = new ServerAddress();
        MongoClientOptions options = new MongoClientOptions.Builder().socketTimeout(3000)
                .minHeartbeatFrequency(25)
                .heartbeatSocketTimeout(3000)
                .build();
        MongoCredential mongoCredential = MongoCredential.createCredential(username, database, password.toCharArray());
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(serverAddress, mongoCredential, options);
        return (MongoClient) mongoClient;
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }
}
