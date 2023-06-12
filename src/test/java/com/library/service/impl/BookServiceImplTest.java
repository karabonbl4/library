package com.library.service.impl;

import com.library.common.TestStorage;
import com.library.model.dto.BookDto;
import com.library.model.mapper.BookMapper;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceImplTest {

    @Mock
    private BookRepository repository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private TestStorage testStorage;

    @BeforeEach
    public void initTestData() {
        this.testStorage = new TestStorage();
    }

    @Test
    @SuppressWarnings("unchecked")
    void findAll() {
        when(repository.findAll(PageRequest.of(0, 5))).thenReturn(new PageImpl(testStorage.getTestBooks()));
        when(bookMapper.mapToBookDto(testStorage.getTestBook())).thenReturn(testStorage.getBookDto());
        List<BookDto> booksDto = bookService.findAllWithPageable(1, 5);

        assertNotNull(booksDto);
        assertEquals(3, booksDto.size());
        verify(repository).findAll(PageRequest.of(0, 5));
    }

    @Test
    void findByID() {
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(testStorage.getTestBook()));
        when(bookMapper.mapToBookDto(testStorage.getTestBook())).thenReturn(testStorage.getBookDto());

        BookDto actual = bookService.findById(1L);

        assertEquals("War and peace", actual.getTitle());
        assertEquals(1L, actual.getId());
        verify(repository).findById(1L);
    }

    @Test
    void saveBook() {
        when(bookMapper.mapToBook(testStorage.getBookDto())).thenReturn(testStorage.getTestBook());
        when(repository.save(testStorage.getTestBook())).thenReturn(testStorage.getTestBook());
        when(bookMapper.mapToBookDto(testStorage.getTestBook())).thenReturn(testStorage.getBookDto());

        BookDto actualBook = bookService.saveOrUpdate(testStorage.getBookDto());

        assertNotNull(actualBook);
        assertEquals(testStorage.getBookDto(), actualBook);
    }

    @Test
    void delete() {
        bookService.deleteById(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void softDelete() {
        when(bookMapper.mapToBook(testStorage.getBookDto())).thenReturn(testStorage.getTestBook());
        when(repository.save(testStorage.getTestBook())).thenReturn(testStorage.getTestBook());
        when(bookMapper.mapToBookDto(testStorage.getTestBook())).thenReturn(testStorage.getBookDto());

        boolean deleted = bookService.softDelete(testStorage.getBookDto()).getDeleted();

        assertTrue(deleted);
    }
}
