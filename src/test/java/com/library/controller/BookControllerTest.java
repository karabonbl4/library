package com.library.controller;

import com.library.model.dto.BookDto;
import com.library.model.entity.Book;
import com.library.repository.BookRepository;
import com.library.common.TestStorage;
import com.library.service.impl.BookServiceImpl;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookServiceImpl bookService;

    @InjectMocks
    private BookController bookController;
    private TestStorage testStorage;

    private JacksonTester<BookDto> jsonBook;

    @BeforeEach
    public void init(){
        testStorage = new TestStorage();
    }

    @SneakyThrows
    @Test
    void getAllBooks() {

        when(bookRepository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl<Book>(testStorage.getTestBooks()));

        MockHttpServletResponse response = mockMvc.perform(get("/books")
                        .param("page", "1")
                        .param("sizeOnPage", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        JSONArray jsonArray = new JSONArray(response.getContentAsString());
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        assertNotNull(response.getContentAsString());
        assertEquals(1, jsonObject.get("id"));
        assertEquals("War and peace", jsonObject.get("title"));
        assertEquals(3, jsonArray.length());
        assertEquals("application/json", response.getContentType());
    }

    @SneakyThrows
    @Test
    void getBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(testStorage.getTestBook()));

        MockHttpServletResponse response = mockMvc.perform(get("/books/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertEquals("application/json", response.getContentType());
        assertEquals("War and peace", jsonObject.get("title"));
        assertEquals(1, jsonObject.get("id"));
    }

    @SneakyThrows
    @Test
    void saveBook() {
        when(bookRepository.save(testStorage.getTestBook())).thenReturn(testStorage.getTestBook());

        MockHttpServletResponse response = mockMvc.perform(post("/books")
                .content(jsonBook.write(testStorage.getBookDto()).getJson()))
                .andReturn()
                .getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(response);
        assertEquals("War and peace", jsonObject.get("title"));
    }

    @Test
    void updateBook() {

    }

    @Test
    void deleteBook() {
    }

    @Test
    void softDeleteBook() {
    }
}