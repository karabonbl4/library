package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.common.TestStorage;
import com.library.model.dto.BookDto;
import com.library.model.entity.Book;
import com.library.model.mapper.BookMapper;
import com.library.repository.BookRepository;
import com.library.utils.IsSameLikeEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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

    @Autowired
    private BookMapper bookMapper;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private TestStorage  testStorage;

    private static final String MISSING_TYPE = "source cannot be null";

    private static final String DELETED_SUCCESS = "Book is deleted successfully!";

    private static final String DATETIME_FORMATTER = "dd-MM-yyyy hh:mm:ss";

    @SneakyThrows
    @Test
    void getAllBooks() {
        Pageable paging = PageRequest.of(0, 5);
        when(bookRepository.findAll(paging)).thenReturn(new PageImpl<>(testStorage.getBooks()));

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
        verify(bookRepository).findAll(paging);
    }

    @SneakyThrows
    @Test
    void getBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testStorage.getBook()));

        MockHttpServletResponse response = mockMvc.perform(get("/books/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertEquals("update", jsonObject.get("title"));
        assertEquals(1, jsonObject.get("id"));
        verify(bookRepository).findById(1L);
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

        assertEquals(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMATTER)), jsonObject.get("timestamp"));
    }

    @SneakyThrows
    @Test
    void saveBook() {
        Book newBook = testStorage.getNewBook();
        BookDto bookDto = bookMapper.mapToBookDto(newBook);
        newBook.setPublisher(testStorage.getPublisher());
        newBook.setAuthors(testStorage.getAuthors());

        when(bookRepository.save(argThat(new IsSameLikeEntity<>(newBook)))).thenReturn(newBook);

        MockHttpServletResponse response = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(response);
        assertEquals(testStorage.getBook().getTitle(), jsonObject.get("title"));
        verify(bookRepository).save(argThat(new IsSameLikeEntity<>(newBook)));
    }

    @SneakyThrows
    @Test
    void saveBookFailed() {
        Book newBook = testStorage.getNewBook();
        newBook.setAuthors(null);
        BookDto bookDto = bookMapper.mapToBookDto(newBook);


        MockHttpServletResponse response = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(response);
        assertEquals(MISSING_TYPE, jsonObject.get("message"));
    }

    @SneakyThrows
    @Test
    void updateBook() {
        Book book = testStorage.getBook();
        book.setTitle("update");
        BookDto bookDto = bookMapper.mapToBookDto(book);

        when(bookRepository.getReferenceById(book.getId())).thenReturn(book);
        when(bookRepository.save(argThat(new IsSameLikeEntity<>(book)))).thenReturn(book);

        MockHttpServletResponse response = mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(response);
        assertEquals(book.getTitle(), jsonObject.get("title"));
        verify(bookRepository).save(argThat(new IsSameLikeEntity<>(book)));
        verify(bookRepository).getReferenceById(book.getId());
    }

    @SneakyThrows
    @Test
    void softDeleteBook() {
        doNothing().when(bookRepository).delete(1L);

        MvcResult mvcResult = mockMvc.perform(delete("/books/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals(DELETED_SUCCESS, response);
        verify(bookRepository).delete(1L);
    }
}