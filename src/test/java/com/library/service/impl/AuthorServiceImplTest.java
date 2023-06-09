package com.library.service.impl;

import com.library.model.dto.AuthorDto;
import com.library.model.entity.Author;
import com.library.model.mapper.AuthorMapper;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorServiceImplTest {

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorServiceImpl authorService;

    private static final Long ID = 1L;

    private Author testAuthor;

    private AuthorDto testAuthorDto;

    @BeforeEach
    public void initData() {
        testAuthor = new Author();
        testAuthor.setId(ID);
        testAuthor.setName("Name");
        testAuthor.setSurname("Surname");
        testAuthor.setBirthDay(LocalDate.of(1999, 5, 17));
        testAuthor.setDeleted(false);

        testAuthorDto = new AuthorDto();
        testAuthorDto.setId(ID);
        testAuthorDto.setFullName("Name Surname");
        testAuthorDto.setBirthDay(LocalDate.of(1999, 5, 17));
        testAuthorDto.setDeleted(false);
    }

    @Test
    void shouldFindAllWithPageable() {
        Page<Author> page = new PageImpl<>(new ArrayList<>(List.of(
                testAuthor,
                testAuthor,
                testAuthor)));
        Pageable paging = PageRequest.of(0, 5);

        when(authorRepository.findAll(paging)).thenReturn(page);

        List<AuthorDto> authorsDto = authorService.findAllWithPageable(1, 5);

        assertEquals(3, authorsDto.size());
    }

    @Test
    void shouldConvertDtoToEntitySuccessful(){
        AuthorDto authorDto = authorMapper.mapToAuthorDto(testAuthor);

        Author author = authorMapper.mapToAuthor(testAuthorDto);

        assertEquals(author.getId(), authorDto.getId());
    }

    @Test
    void shouldFindAuthorByIdSuccessful() {
        when(authorRepository.findById(ID)).thenReturn(Optional.ofNullable(testAuthor));


        AuthorDto actual = authorService.findById(ID);

        assertNotNull(actual);
        assertEquals("Name Surname", actual.getFullName());
        verify(authorRepository).findById(ID);
    }

    @Test
    void shouldSaveOrUpdateAuthorSuccessful() {
        when(authorRepository.save(testAuthor)).thenReturn(testAuthor);

        AuthorDto actualAuthor = authorService.saveOrUpdate(testAuthorDto);

        assertNotNull(actualAuthor);
        assertEquals(testAuthorDto, actualAuthor);
    }

    @Test
    void shouldDeleteAuthorById() {
        authorService.deleteById(ID);

        verify(authorRepository).deleteById(ID);
    }

    @Test
    void shouldSoftDeleteAuthorSuccessful() {
        when(authorRepository.save(testAuthor)).thenReturn(testAuthor);

        boolean deleted = authorService.softDelete(testAuthorDto).getDeleted();

        assertTrue(deleted);
    }
}