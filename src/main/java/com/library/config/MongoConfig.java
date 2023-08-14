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
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.library.constant.MongoConstant.DATABASE_FACTORY;
import static com.library.constant.MongoConstant.DATABASE_PROPERTY;
import static com.library.constant.MongoConstant.DATA_SOURCE;
import static com.library.constant.MongoConstant.MANAGER_FACTORY;
import static com.library.constant.MongoConstant.PROPERTY_PREFIX;
import static com.library.constant.MongoConstant.REPOSITORY_PACKAGE;
import static com.library.constant.MongoConstant.TRANSACTION_MANAGER;
import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(
        basePackages = {REPOSITORY_PACKAGE},
        mongoTemplateRef = MANAGER_FACTORY)
@EnableConfigurationProperties
public class MongoConfig {

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

    @Bean(DATABASE_FACTORY)
    public MongoDatabaseFactory mongoDatabaseFactory(
            @Qualifier(DATA_SOURCE) MongoClient mongoClient,
            @Qualifier(DATABASE_PROPERTY) MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean(MANAGER_FACTORY)
    public MongoTemplate mongoTemplate(@Qualifier(DATABASE_FACTORY) MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

    @Bean(TRANSACTION_MANAGER)
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory mongoDatabaseFactory){
        return new MongoTransactionManager(mongoDatabaseFactory);
    }
}
