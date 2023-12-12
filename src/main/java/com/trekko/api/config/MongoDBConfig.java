package com.trekko.api.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import dev.morphia.Datastore;
import dev.morphia.Morphia;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoDbConfig extends AbstractMongoClientConfiguration {

    private static final String USER = "main";
    private static final String PASSWORD = "uKHnG1airuZyHPGe";
    private static final String DATABASE = "trekko";

    @Override
    protected String getDatabaseName() {
        return DATABASE;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        final ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://main:uKHnG1airuZyHPGe@trekko.3vwe8kl.mongodb.net/?retryWrites=true&w=majority"); // TODO
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public Datastore datastore() {
        return Morphia.createDatastore(mongoClient(), getDatabaseName());
    }
}
