package com.library.service.implementation;

import com.library.model.dto.AuthorDto;
import com.library.model.entity.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private static final Long ID = 1L;

    @Test
    void shouldFindAuthorByIdSuccessful() {
        final AuthorDto authorDto = mock(AuthorDto.class);
        final Author mappedAuthor = mock(Author.class);
        when(modelMapper.map(authorDto, Author.class)).thenReturn(mappedAuthor);
        when(authorRepository.findById(ID)).thenReturn(Optional.ofNullable(mappedAuthor));
        when(modelMapper.map(mappedAuthor, AuthorDto.class)).thenReturn(authorDto);

        final AuthorDto actual = authorService.findById(ID);

        assertNotNull(actual);
        assertEquals(authorDto, actual);
        verify(authorRepository).findById(ID);
    }

    @Test
    void shouldSaveOrUpdateAuthorSuccessful() {
        final AuthorDto authorDto = mock(AuthorDto.class);
        final Author mappedAuthor = mock(Author.class);
        when(modelMapper.map(authorDto, Author.class)).thenReturn(mappedAuthor);
        when(authorRepository.save(mappedAuthor)).thenReturn(mappedAuthor);
        when(modelMapper.map(mappedAuthor, AuthorDto.class)).thenReturn(authorDto);

        final AuthorDto actualAuthor = authorService.saveOrUpdate(authorDto);

        assertNotNull(actualAuthor);
        assertEquals(authorDto, actualAuthor);
        verify(authorRepository).save(mappedAuthor);
    }

    @Test
    void shouldDeleteAuthorById() {
        authorService.deleteById(ID);

        verify(authorRepository).deleteById(ID);
    }

    @Test
    void shouldSoftDeleteAuthorSuccessful() {
        AuthorDto authorDto = new AuthorDto();
        Author author = new Author();
        author.setDeleted(true);

        when(modelMapper.map(authorDto, Author.class)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(modelMapper.map(author, AuthorDto.class)).thenReturn(authorDto);


        final boolean deleted = authorService.softDelete(authorDto).getDeleted();

        assertTrue(deleted);
        verify(authorRepository).save(author);
    }
}