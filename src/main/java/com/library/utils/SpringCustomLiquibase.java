package com.library.utils;

import liquibase.Contexts;
import liquibase.GlobalConfiguration;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.configuration.ConfiguredValue;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.OfflineConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.ext.mongodb.database.MongoConnection;
import liquibase.ext.mongodb.database.MongoLiquibaseDatabase;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.integration.spring.SpringResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class SpringCustomLiquibase extends SpringLiquibase {

    private MongoConnection mongoConnection;

    private String changeLog;

    private ResourceLoader resourceLoader;


    @Override
    public void afterPropertiesSet() throws LiquibaseException {
        final ConfiguredValue<Boolean> shouldRunProperty = LiquibaseCommandLineConfiguration.SHOULD_RUN.getCurrentConfiguredValue();

        if (!(Boolean) shouldRunProperty.getValue()) {
            Scope.getCurrentScope().getLog(getClass()).info("Liquibase did not run because " + shouldRunProperty.getProvidedValue().describe() + " was set to false");
            return;
        }
        if (!shouldRun) {
            Scope.getCurrentScope().getLog(getClass()).info("Liquibase did not run because 'shouldRun' " + "property was set " +
                    "to false on " + getBeanName() + " Liquibase Spring bean.");
            return;
        }

        try (Liquibase liquibase = createLiquibase()) {
            generateRollbackFile(liquibase);
            performUpdate(liquibase);
        }
    }

    private void generateRollbackFile(Liquibase liquibase) throws LiquibaseException {
        if (rollbackFile != null) {

            try (
                    final OutputStream outputStream = Files.newOutputStream(rollbackFile.toPath());
                    Writer output = new OutputStreamWriter(outputStream, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue())) {

                if (tag != null) {
                    liquibase.futureRollbackSQL(tag, new Contexts(getContexts()),
                            new LabelExpression(getLabelFilter()), output);
                } else {
                    liquibase.futureRollbackSQL(new Contexts(getContexts()), new LabelExpression(getLabelFilter()), output);
                }
            } catch (IOException e) {
                throw new LiquibaseException("Unable to generate rollback file.", e);
            }
        }
    }

    protected void performUpdate(Liquibase liquibase) throws LiquibaseException {
        if (isClearCheckSums()) {
            liquibase.clearCheckSums();
        }

        if (isTestRollbackOnUpdate()) {
            if (tag != null) {
                liquibase.updateTestingRollback(tag, new Contexts(getContexts()), new LabelExpression(getLabelFilter()));
            } else {
                liquibase.updateTestingRollback(new Contexts(getContexts()), new LabelExpression(getLabelFilter()));
            }
        } else {
            if (tag != null) {
                liquibase.update(tag, new Contexts(getContexts()), new LabelExpression(getLabelFilter()));
            } else {
                liquibase.update(new Contexts(getContexts()), new LabelExpression(getLabelFilter()));
            }
        }
    }

    protected Liquibase createLiquibase() throws LiquibaseException {
        SpringResourceAccessor resourceAccessor = new SpringResourceAccessor(resourceLoader);
        Liquibase liquibase = new Liquibase(changeLog, resourceAccessor, createDatabase(resourceAccessor));
        if (parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                liquibase.setChangeLogParameter(entry.getKey(), entry.getValue());
            }
        }

        if (isDropFirst()) {
            liquibase.dropAll();
        }

        return liquibase;
    }

    protected Database createDatabase(ResourceAccessor resourceAccessor) throws DatabaseException {

        DatabaseConnection liquibaseConnection;
        if (mongoConnection == null) {
            log.warning("Null connection returned by liquibase datasource. Using offline unknown database");
            liquibaseConnection = new OfflineConnection("offline:unknown", resourceAccessor);

        } else {
            liquibaseConnection = mongoConnection;
        }

        MongoLiquibaseDatabase database = (MongoLiquibaseDatabase) DatabaseFactory.getInstance().findCorrectDatabaseImplementation(liquibaseConnection);
        database.setDefaultSchemaName("library");
        database.setCurrentDateTimeFunction("NOW()");
        database.setAutoCommit(true);
        database.setDatabaseChangeLogTableName(Database.databaseChangeLogTableName);
        database.setDatabaseChangeLogLockTableName(Database.databaseChangeLogLockTableName);
        database.setAdjustTrackingTablesOnStartup(true);
        database.setSupportsValidator(true);

        if (StringUtil.trimToNull(database.getDefaultSchemaName()) != null) {
            if (database.supportsSchemas()) {
                database.setDefaultSchemaName(database.getDefaultSchemaName());
            } else if (database.supportsCatalogs()) {
                database.setDefaultCatalogName(database.getDefaultSchemaName());
            }
        }
        if (StringUtil.trimToNull(database.getDefaultSchemaName()) != null) {
            if (database.supportsSchemas()) {
                database.setLiquibaseSchemaName(database.getDefaultSchemaName());
            } else if (database.supportsCatalogs()) {
                database.setLiquibaseCatalogName(database.getDefaultSchemaName());
            }
        }
        if (StringUtil.trimToNull(this.liquibaseTablespace) != null && database.supportsTablespaces()) {
            database.setLiquibaseTablespaceName(this.liquibaseTablespace);
        }
        if (StringUtil.trimToNull(database.getDatabaseChangeLogTableName()) != null) {
            database.setDatabaseChangeLogTableName(database.getDatabaseChangeLogTableName());
        }
        if (StringUtil.trimToNull(database.getDatabaseChangeLogLockTableName()) != null) {
            database.setDatabaseChangeLogLockTableName(database.getDatabaseChangeLogLockTableName());
        }
        return database;
    }
}