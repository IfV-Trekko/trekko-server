package com.trekko.api.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import dev.morphia.Datastore;
import dev.morphia.Morphia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoDbConfig extends AbstractMongoClientConfiguration {

    private static final String DATABASE = "trekko";

    @Value("${mongo.user:main}")
    private String user;

    @Value("${mongo.password:}")
    private String password;

    @Value("${mongo.host:trekko.3vwe8kl.mongodb.net}")
    private String host;

    @Value("${mongo.port:}")
    private String port;

    @Override
    @Bean
    public MongoClient mongoClient() {
        final var connectionString = new ConnectionString(this.buildConnectionString());
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public Datastore datastore() {
        return Morphia.createDatastore(this.mongoClient(), this.getDatabaseName());
    }

    @Override
    protected String getDatabaseName() {
        return DATABASE;
    }

    private String buildConnectionString() {
        final StringBuilder connectionString = new StringBuilder("mongodb://");
        connectionString.append(user);
        if (!password.isEmpty()) {
            connectionString.append(":").append(password);
        }
        connectionString.append("@").append(host);
        if (!port.isEmpty()) {
            connectionString.append(":").append(port);
        }
        connectionString.append("/?retryWrites=true&w=majority");
        return connectionString.toString();
    }
}
