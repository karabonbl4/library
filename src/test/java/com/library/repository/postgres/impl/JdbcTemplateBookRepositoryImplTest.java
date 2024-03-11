package com.library.repository.postgres.impl;

import com.library.model.entity.Book;
import com.library.model.entity.Publisher;
import com.library.repository.postgres.PublisherRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class JdbcTemplateBookRepositoryImplTest {

    @Autowired
    JdbcTemplateBookRepositoryImpl jdbcTemplateBookRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @SuppressWarnings("rawtypes")
    @Container
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
    void saveEntity() {
        Publisher publisher = publisherRepository.getReferenceById(1L);
        Book bookForSave = new Book();
        bookForSave.setTitle("Мартен Фуркад: последний круг");
        bookForSave.setPublicationYear(Short.valueOf("2022"));
        bookForSave.setStack(78797);
        bookForSave.setUnit("E");
        bookForSave.setInventoryNumber("sda54");
        bookForSave.setPublisher(publisher);
        Book savedBook = jdbcTemplateBookRepository.save(bookForSave);

        assertNotNull(savedBook.getId());
        assertEquals(bookForSave.getTitle(), savedBook.getTitle());
    }

    @AfterAll
    public static void destroy(){
        container.stop();
    }
}