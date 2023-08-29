package com.library.config;

import com.library.utils.SpringCustomLiquibase;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import liquibase.Liquibase;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.ext.mongodb.database.MongoClientDriver;
import liquibase.ext.mongodb.database.MongoConnection;
import liquibase.ext.mongodb.database.MongoLiquibaseDatabase;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.integration.spring.SpringResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.util.HashMap;

import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(
        basePackages = {"com.library.repository.mongo"})
@EnableConfigurationProperties
public class MongoConfig {

    @Autowired
    private ResourceLoader resourceLoader;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "app.mongo.datasource")
    public MongoProperties mongoProperties() {
        return new MongoProperties();
    }

    @Bean
    public MongoClient mongoClient(MongoProperties mongoProperties) {
        MongoCredential credential = MongoCredential
                .createCredential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());

        return MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder
                        .hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
                .credential(credential)
                .build());
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(
            MongoClient mongoClient,
            MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory, MappingMongoConverter mongoConverter) {
        mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(mongoDatabaseFactory, mongoConverter);
    }

    @Bean
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.mongo.liquibase")
    public LiquibaseProperties mongoLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase mongoLiquibase(LiquibaseProperties mongoLiquibaseProperties, MongoTemplate mongoTemplate) throws LiquibaseException {
        MongoConnection mongoConnection = new MongoConnection();
        mongoConnection.setMongoClient(mongoClient(mongoProperties()));
        mongoConnection.setMongoDatabase(mongoTemplate.getMongoDatabaseFactory().getMongoDatabase());
        mongoConnection.setConnectionString(new ConnectionString(mongoLiquibaseProperties.getUrl()));
        mongoConnection.setAutoCommit(true);

        return new SpringCustomLiquibase(mongoConnection, mongoLiquibaseProperties.getChangeLog(), resourceLoader);
    }
}
