package com.library.service;

import com.library.model.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAllWithPageable(Integer page, Integer sizeOnPage);

    BookDto findById(Long id);

    BookDto saveOrUpdate(BookDto bookDto);

    void softDelete(Long bookId);
}