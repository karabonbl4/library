package com.library.service.impl;

import com.library.model.dto.BookDto;
import com.library.model.entity.postgres.Book;
import com.library.model.mapper.BookMapper;
import com.library.repository.postgres.BookRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation of BookService. Contains business logic for Book entity
 *
 * @author Lesha Korobko
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    /**
     * Method receives two Integer parameters,
     * creates Pageable based on them,
     * converts result from repository layer in List of BookDto
     *
     * @param page       requested page, must not be negative
     * @param sizeOnPage number of elements on page, must be greater than 0
     * @return a list of elements according to the number and order specified in the parameters, with default sorting by id
     */
    @Override
    public List<BookDto> findAllWithPageable(Integer page, Integer sizeOnPage) {
        Pageable paging = PageRequest.of(page - 1, sizeOnPage, Sort.by("id").ascending());
        return bookRepository.findAll(paging).stream()
                .map(bookMapper::mapToBookDto)
                .collect(Collectors.toList());
    }

    /**
     * @param id specific book id from database, must be greater than 0
     * @return BookDto with requested id or throws exception
     * @throws EntityNotFoundException if entity with requested id does not exist
     */
    @Override
    public BookDto findById(Long id) {
        return bookMapper.mapToBookDto(bookRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * @param bookDto an instance of the BookDto class to save in the database,
     *                must not contain null fields: authors, publisher
     * @return saved or updated bookDto
     */
    @Override
    public BookDto saveOrUpdate(BookDto bookDto) {
        Book savedBook = bookMapper.mapToBook(bookDto);
        return bookMapper.mapToBookDto(bookRepository.save(savedBook));
    }

    /**
     * @param bookId specific book id from database, must be greater than 0
     * @throws EntityNotFoundException if entity with requested id does not exist
     */
    @Override
    public void softDelete(Long bookId) {
        bookRepository.delete(bookId);
    }

    @Override
    public void hardDelete(Long bookId) {
        bookRepository.hardDelete(bookId);
    }
}