package com.library.repository.postgres.impl;

import com.library.model.entity.Book;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class CriteriaBookRepositoryImplTest {

    @Autowired
    CriteriaBookRepositoryImpl criteriaBookRepository;

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

    @Test
    @Sql(scripts = "classpath:sql_script/initTestDB.sql")
    void findByStackAndUnit() {

        List<Book> books = criteriaBookRepository.findByStackAndUnit(12, "A");

        assertEquals(1, books.size());
        assertNotNull(books.get(0).getPublisher().getTitle());
    }

    @Test
    @SneakyThrows
    void softDeleteById() {
        criteriaBookRepository.softDeleteById(2L);

        Book deletedBook = jdbcBookRepository.getById(2L);

        assertEquals(true, deletedBook.getDeleted());
    }

    @AfterAll
    static void tearDown() {
        container.stop();
    }
}