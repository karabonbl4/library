package com.library.constant;

public interface PostgresConstant {

    String PROPERTY_PREFIX = "app.postgres.datasource";

    String JPA_REPOSITORY_PACKAGE = "com.library.repository.postgres";

    String ENTITY_PACKAGE = "com.library.model.entity.postgres";

    String ENTITY_MANAGER_FACTORY = "postgresEntityManagerFactory";

    String DATA_SOURCE = "postgresDataSource";

    String DATABASE_PROPERTY = "postgresDatabaseProperty";

    String TRANSACTION_MANAGER = "postgresTransactionManager";
}
