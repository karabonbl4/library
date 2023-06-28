package com.library.service.impl;

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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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
        return bookMapper.mapToBookDto(bookRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public BookDto saveOrUpdate(BookDto bookDto) {
        Book savedBook = bookMapper.mapToBook(bookDto);
        return bookMapper.mapToBookDto(bookRepository.save(savedBook));
    }

    @Override
    public void softDelete(Long bookId) {
        bookRepository.delete(bookId);
    }
}