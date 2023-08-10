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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

import static com.library.constant.PostgresConstant.DATABASE_PROPERTY;
import static com.library.constant.PostgresConstant.DATA_SOURCE;
import static com.library.constant.PostgresConstant.ENTITY_MANAGER_FACTORY;
import static com.library.constant.PostgresConstant.ENTITY_PACKAGE;
import static com.library.constant.PostgresConstant.JPA_REPOSITORY_PACKAGE;
import static com.library.constant.PostgresConstant.PROPERTY_PREFIX;
import static com.library.constant.PostgresConstant.TRANSACTION_MANAGER;

@EnableJpaRepositories(
        entityManagerFactoryRef = ENTITY_MANAGER_FACTORY,
        basePackages = JPA_REPOSITORY_PACKAGE,
        transactionManagerRef = TRANSACTION_MANAGER)
@EnableConfigurationProperties
@Configuration
public class PostgresConfig {

    @Bean(DATABASE_PROPERTY)
    @ConfigurationProperties(prefix = PROPERTY_PREFIX)
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(DATA_SOURCE)
    public DataSource appDataSource(
            @Qualifier(DATABASE_PROPERTY) DataSourceProperties postgresDataSourceProperties
    ) {
        return DataSourceBuilder
                .create()
                .username(postgresDataSourceProperties.getUsername())
                .password(postgresDataSourceProperties.getPassword())
                .url(postgresDataSourceProperties.getUrl())
                .driverClassName(postgresDataSourceProperties.getDriverClassName())
                .build();
    }

    @Bean(ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean appEntityManager(
            @Qualifier(DATA_SOURCE) DataSource dataSource
    ) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName(ENTITY_MANAGER_FACTORY);
        em.setPackagesToScan(ENTITY_PACKAGE);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        final Map<String, Object> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("javax.persistence.validation.mode", "none");
        jpaPropertyMap.put("hibernate.hbm2ddl.auto", "update");
        jpaPropertyMap.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaPropertyMap.put("hibernate.format_sql", "true");
        em.setJpaPropertyMap(jpaPropertyMap);
        return em;
    }

    @Primary
    @Bean(TRANSACTION_MANAGER)
    public PlatformTransactionManager sqlSessionTemplate(
            @Qualifier(ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManager,
            @Qualifier(DATA_SOURCE) DataSource dataSource
    ) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager.getObject());
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
