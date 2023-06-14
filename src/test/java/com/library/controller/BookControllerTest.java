package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.common.TestStorage;
import com.library.model.entity.Book;
import com.library.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookRepository bookRepository;

    private TestStorage testStorage;

    @BeforeEach
    public void init() {
        testStorage = new TestStorage();
    }

    @SneakyThrows
    @Test
    void getAllBooks() {
        Pageable paging = PageRequest.of(0, 5);
        when(bookRepository.findAll(paging)).thenReturn(new PageImpl<Book>(testStorage.getTestBooks()));

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
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testStorage.getTestBook()));

        MockHttpServletResponse response = mockMvc.perform(get("/books/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(jsonObject);
        assertEquals("application/json", response.getContentType());
        assertEquals("War and peace", jsonObject.get("title"));
        assertEquals(1, jsonObject.get("id"));
    }

    @SneakyThrows
    @Test
    void getBookByIdFailed() {
        when(bookRepository.findById(1L)).thenThrow(EntityNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(get("/books/{id}", "1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertEquals("application/json", response.getContentType());
        assertEquals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")), jsonObject.get("timestamp"));
    }

    @SneakyThrows
    @Test
    void saveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(testStorage.getTestBook());

        MockHttpServletResponse response = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStorage.getBookDto())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(response);
        assertEquals("War and peace", jsonObject.get("title"));
    }

    @SneakyThrows
    @Test
    void updateBook() {
        Book book = testStorage.getTestBook();
        book.setTitle("update");
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        MockHttpServletResponse response = mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStorage.getBookDto())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(response);
        assertEquals("update", jsonObject.get("title"));
    }

    @SneakyThrows
    @Test
    void softDeleteBook() {
        MvcResult mvcResult = mockMvc.perform(delete("/books/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals(response, "Book is deleted successfully!");
    }
}