package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.common.TestStorage;
import com.library.controller.atheneum.BookController;
import com.library.model.dto.BookDto;
import com.library.model.entity.Book;
import com.library.model.mapper.BookMapper;
import com.library.repository.postgres.BookRepository;
import com.library.service.atheneum.BookService;
import com.library.service.atheneum.impl.BookServiceImpl;
import com.library.utils.IsSameLikeBook;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static com.library.constant.ApplicationConstant.BOOK_IS_DELETED;
import static com.library.constant.ApplicationConstant.FIELD_NOT_PRESENT;
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

@WebMvcTest(BookController.class)
@Import(value = {BookServiceImpl.class, ModelMapper.class, BookMapper.class, TestStorage.class})
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookMapper bookMapper;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private TestStorage  testStorage;

    private static final String MISSING_TYPE = "source cannot be null";

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

        assertNotNull(response.getContentAsString());
        assertEquals(3, jsonArray.length());
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
        when(bookRepository.findById(4L)).thenThrow(EntityNotFoundException.class);

        MockHttpServletResponse response = mockMvc.perform(get("/books/{id}", "4"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertEquals("null", jsonObject.get("message").toString());
        verify(bookRepository).findById(4L);
    }

    @SneakyThrows
    @Test
    void saveBook() {
        Book newBook = testStorage.getNewBook();
        BookDto bookDto = bookMapper.mapToBookDto(newBook);

        when(bookRepository.save(argThat(new IsSameLikeBook(newBook)))).thenReturn(newBook);

        MockHttpServletResponse response = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(response);
        assertEquals(testStorage.getBook().getTitle(), jsonObject.get("title"));
        verify(bookRepository).save(argThat(new IsSameLikeBook(newBook)));
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

        when(bookRepository.save(argThat(new IsSameLikeBook(book)))).thenReturn(book);

        MockHttpServletResponse response = mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(response);
        assertEquals(book.getTitle(), jsonObject.get("title"));
        verify(bookRepository).save(argThat(new IsSameLikeBook(book)));
    }

    @SneakyThrows
    @Test
    void updateBookFailedWithMethodArgumentNotValidException() {
        Book book = testStorage.getBook();
        book.setAuthors(null);
        book.setPublisher(null);
        BookDto bookDto = bookMapper.mapToBookDto(book);

        MockHttpServletResponse response = mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        assertNotNull(response);
        assertEquals(FIELD_NOT_PRESENT, jsonObject.get("message"));
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

        assertEquals(BOOK_IS_DELETED, response);
        verify(bookRepository).delete(1L);
    }
}