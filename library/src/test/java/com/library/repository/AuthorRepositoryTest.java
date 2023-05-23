package com.library.repository;

import com.library.model.entity.Author;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private static final Long ID = 1L;

    @Test
    void shouldGetAuthorById(){
        Author author = authorRepository.findById(ID).orElseThrow(EntityNotFoundException::new);

        assertEquals(ID, author.getId());
    }


}