package com.library.repository.postgres.impl;

import com.library.model.entity.Book;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class JdbcBookRepositoryImplTest {

    @Autowired
    JdbcBookRepositoryImpl jdbcBookRepository;

    @Container
    @SuppressWarnings("rawtypes")
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName("library")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeAll
    public static void init(){
        container.withReuse(true);
        container.start();
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry){
        registry.add("app.postgres.datasource.url", container::getJdbcUrl);
        registry.add("app.postgres.datasource.username", container::getUsername);
        registry.add("app.postgres.datasource.password", container::getPassword);
        registry.add("app.postgres.datasource.driver-class-name", container::getDriverClassName);
    }

    @SneakyThrows
    @Test
    @Sql(scripts = "classpath:sql_script/initTestDB.sql")
    void getById() {
        Book expectedBook = jdbcBookRepository.getById(2L);

        assertNotNull(expectedBook.getTitle());
    }

    @SneakyThrows
    @Test
    void getByIdShouldThrowSQLException() {

        assertThrows(SQLException.class, ()-> jdbcBookRepository.getById(1000L));
    }

    @AfterAll
    public static void destroy(){
        container.stop();
    }
}