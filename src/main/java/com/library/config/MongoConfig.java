package com.library.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(
        basePackages = {MongoConfig.JPA_REPOSITORY_PACKAGE},
        mongoTemplateRef = MongoConfig.ENTITY_MANAGER_FACTORY)
@EnableConfigurationProperties
public class MongoConfig {

    public static final String PROPERTY_PREFIX = "app.mongo.datasource";
    public static final String JPA_REPOSITORY_PACKAGE = "com.library.repository.mongo";
    public static final String ENTITY_MANAGER_FACTORY = "mongoEntityManagerFactory";
    public static final String DATA_SOURCE = "mongoDataSource";
    public static final String DATABASE_PROPERTY = "mongoDatabaseProperty";
    public static final String TRANSACTION_MANAGER = "mongoTransactionManager";

    @Primary
    @Bean(DATABASE_PROPERTY)
    @ConfigurationProperties(PROPERTY_PREFIX)
    public MongoProperties mongoProperties() {
        return new MongoProperties();
    }

    @Bean(DATA_SOURCE)
    public MongoClient mongoClient(@Qualifier(DATABASE_PROPERTY) MongoProperties mongoProperties) {

        MongoCredential credential = MongoCredential
                .createCredential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());

        return MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder
                        .hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
                .credential(credential)
                .build());
    }

    @Bean(TRANSACTION_MANAGER)
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier(DATA_SOURCE) MongoClient mongoClient,
            @Qualifier(DATABASE_PROPERTY) MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean(ENTITY_MANAGER_FACTORY)
    public MongoTemplate mongoTemplate(@Qualifier(TRANSACTION_MANAGER) MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
