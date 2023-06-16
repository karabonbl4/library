package com.library.service.impl;

import com.library.exception.MissingRequiredDataException;
import com.library.model.dto.BookDto;
import com.library.model.entity.Book;
import com.library.model.mapper.BookMapper;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    public List<BookDto> findAllWithPageable(Integer page, Integer sizeOnPage) {
        Pageable paging = PageRequest.of(page - 1, sizeOnPage);
        return bookRepository.findAll(paging).stream()
                .map(bookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Long id) {
        validateId(id);
        return bookMapper.mapToBookDto(bookRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public BookDto save(BookDto bookDto) {
        Book savedBook = bookMapper.mapToBook(bookDto);
        this.validateBook(savedBook);
        return bookMapper.mapToBookDto(bookRepository.save(savedBook));
    }

    @Override
    public BookDto update(BookDto bookDto) {
        Book updateBook = bookMapper.mapToBook(bookDto);
        this.validateId(bookDto.getId());
        return bookMapper.mapToBookDto(bookRepository.save(updateBook));
    }

    @Override
    public void softDelete(Long bookId) {
        this.validateId(bookId);
        bookRepository.delete(bookId);
    }

    private void validateId(Long id){
        if (id == null || id < 1L) {
            throw new InvalidParameterException("id must be 1 or more");
        }
    }

    private void validateBook(Book book){
        List<String> missingType = new ArrayList<>();
        if (book.getAuthors() == null){
            missingType.add("authors");
        }
        if (book.getPublisher() == null) {
            missingType.add("publisher");
        }
        if (missingType.size() > 0){
            throw new MissingRequiredDataException(missingType);
        }
    }
}
