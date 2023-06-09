package com.library.controller;

import com.library.model.dto.AuthorDto;
import com.library.model.entity.Author;
import com.library.model.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.service.impl.AuthorServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorServiceImpl authorService;

    @MockBean
    private AuthorRepository authorRepository;

    private Author testAuthor;

    @BeforeEach
    public void initData() {
        testAuthor = new Author();
        testAuthor.setId(1L);
        testAuthor.setName("Name");
        testAuthor.setSurname("Surname");
        testAuthor.setBirthDay(LocalDate.of(1999, 5, 17));
        testAuthor.setDeleted(false);
        testAuthor.setBooks(List.of(new Book()));
    }

    @Test
    @SneakyThrows
    void getAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.ofNullable(testAuthor));

        MvcResult mvcResult = mockMvc.perform(get("/authors/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

}