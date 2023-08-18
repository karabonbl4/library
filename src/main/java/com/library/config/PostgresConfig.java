package com.library.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;


@EnableJpaRepositories(
        basePackages = {"com.library.repository.postgres"})
@EnableConfigurationProperties
@Configuration
public class PostgresConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.postgres.datasource")
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource appDataSource(DataSourceProperties postgresDataSourceProperties) {
        return DataSourceBuilder
                .create()
                .username(postgresDataSourceProperties.getUsername())
                .password(postgresDataSourceProperties.getPassword())
                .url(postgresDataSourceProperties.getUrl())
                .driverClassName(postgresDataSourceProperties.getDriverClassName())
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName("em");
        em.setPackagesToScan("com.library.model.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        final Map<String, Object> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("jakarta.persistence.validation.mode", "none");
        jpaPropertyMap.put("hibernate.hbm2ddl.auto", "validate");
        jpaPropertyMap.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaPropertyMap.put("hibernate.show_sql", "true");
        jpaPropertyMap.put("hibernate.implicit_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        jpaPropertyMap.put("hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        em.setJpaPropertyMap(jpaPropertyMap);
        return em;
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            LocalContainerEntityManagerFactoryBean entityManager,
            DataSource dataSource) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager.getObject());
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager chainedTransactionManager(
            @Qualifier("transactionManager") PlatformTransactionManager transactionManager,
            @Qualifier("mongoTransactionManager") PlatformTransactionManager mongoTransactionManager) {
        return new ChainedTransactionManager(transactionManager, mongoTransactionManager);
    }
}